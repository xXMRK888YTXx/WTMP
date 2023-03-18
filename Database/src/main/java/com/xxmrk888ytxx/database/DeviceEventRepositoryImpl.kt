package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.RemoveEventImageUseCase
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity
import com.xxmrk888ytxx.database.Entity.DeviceEventEntity
import com.xxmrk888ytxx.database.Entity.UnlockDeviceEventEntity
import com.xxmrk888ytxx.database.models.DeviceEventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeviceEventRepositoryImpl @Inject constructor(
    private val context: Context,
    private val removeEventImageUseCase: RemoveEventImageUseCase,
    private val maxStorageReportUseCase: MaxStorageReportUseCase,
    private val maxTimeStorageReportUseCase: MaxTimeStorageReportUseCase
) : DeviceEventRepository {

    private val dataBaseComponent: DataBaseComponent by lazy {
        DaggerDataBaseComponent.factory().create(context)
    }

    private val deviceEventDao by lazy { dataBaseComponent.deviceEventDao }

    private val appOpenEventDao by lazy { dataBaseComponent.appOpenEventDao }

    private val unlockDeviceEventDao by lazy { dataBaseComponent.unlockDeviceEvent }

    private val mutex = Mutex()

    private var validateEventListActiveJob:Job? = null
    private fun validateEventList() {
        if(validateEventListActiveJob?.isActive == true) return

         validateEventListActiveJob = ApplicationScope.launch(Dispatchers.IO) {
             maxStorageReportUseCase.execute(this@DeviceEventRepositoryImpl)
             maxTimeStorageReportUseCase.execute(this@DeviceEventRepositoryImpl)

             validateEventListActiveJob = null
         }
    }

    override fun getAllEvents(): Flow<List<DeviceEvent>> {
        return deviceEventDao.getAllEvents().map { eventList ->
            mutex.withLock {
                eventList.mapNotNull { it.mapToDeviceEvent() }
            }
        }
    }

    override fun getEventInTimeSpan(start: Long, end: Long): Flow<List<DeviceEvent>> {
        return deviceEventDao.getEventInTimeSpan(start, end).map { eventList ->
            mutex.withLock {
                eventList.mapNotNull { it.mapToDeviceEvent() }
            }
        }
    }

    override fun getEvent(eventId: Int): Flow<DeviceEvent> {
        return deviceEventDao.getEvent(eventId).map {
            mutex.withLock {
               it.mapToDeviceEvent() ?: DeviceEvent.AttemptUnlockDevice.Failed(1,1L)
            }
        }
    }

    override suspend fun addEvent(deviceEvent: DeviceEvent) : Int {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                val eventId = registerEvent(deviceEvent)
                when (deviceEvent) {
                    is DeviceEvent.AppOpen -> {
                        appOpenEventDao.addEvent(deviceEvent.mapToEntity(eventId))
                    }

                    is DeviceEvent.AttemptUnlockDevice -> {
                        unlockDeviceEventDao.addEvent(deviceEvent.mapToEntity(eventId))
                    }

                    is DeviceEvent.DeviceLaunch -> {}
                }
            }

            validateEventList()

            return deviceEventDao.getLastEventId()
        }
    }

    private suspend fun registerEvent(deviceEvent: DeviceEvent): Int {
        deviceEventDao.addEvent(deviceEvent.mapToEventEntity())
        return deviceEventDao.getLastEventId()
    }

    override suspend fun removeEvent(eventId: Int) {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                deviceEventDao.removeEvent(eventId)
            }
        }

        ApplicationScope.launch(Dispatchers.IO) {
            removeEventImageUseCase.execute(eventId)
        }

        validateEventList()
    }


    private suspend fun DeviceEventModel.mapToDeviceEvent(): DeviceEvent? {
        return when (eventInfo.eventType) {

            (0).toShort() -> {
                val unlockEvent = this.unlockEvents ?: return null
                unlockEvent.mapToDeviceEvent(eventInfo)
            }

            (1).toShort() -> {
                val appOpenEvent = this.appOpenEvents ?: return null
                appOpenEvent.mapToDeviceEvent(eventInfo)
            }

            (2).toShort() -> {
                val deviceLaunchEvent = this.eventInfo
                DeviceEvent.DeviceLaunch(deviceLaunchEvent.eventId,deviceLaunchEvent.time)
            }

            else -> error("Cannot be converted to DeviceEvent." +
                    "Not known eventType(${eventInfo.eventType})")
        }
    }

    private suspend fun DeviceEvent.mapToEventEntity(): DeviceEventEntity {
        return DeviceEventEntity(
            time = this.time,
            eventType = deviceEventToId()
        )
    }

    //
    private suspend fun DeviceEvent.AppOpen.mapToEntity(eventId: Int): AppOpenEventEntity {
        return AppOpenEventEntity(
            eventId = eventId,
            packageName = this.packageName
        )
    }

    private suspend fun AppOpenEventEntity.mapToDeviceEvent(
        eventInfo: DeviceEventEntity,
    ): DeviceEvent.AppOpen {
        return DeviceEvent.AppOpen(
            eventId = eventInfo.eventId,
            appName = null,
            packageName = packageName,
            icon = null,
            time = eventInfo.time
        )
    }

    //
    private suspend fun DeviceEvent.AttemptUnlockDevice.mapToEntity(eventId: Int): UnlockDeviceEventEntity {
        return UnlockDeviceEventEntity(
            eventId = eventId,
            lockEventType = lockEventToId()
        )
    }

    private suspend fun UnlockDeviceEventEntity.mapToDeviceEvent(
        eventInfo: DeviceEventEntity,
    ): DeviceEvent.AttemptUnlockDevice {
        return when (this.lockEventType) {
            (0).toShort() -> {
                DeviceEvent.AttemptUnlockDevice.Failed(
                    eventInfo.eventId,
                    eventInfo.time
                )
            }
            (1).toShort() -> {
                DeviceEvent.AttemptUnlockDevice.Succeeded(
                    eventInfo.eventId,
                    eventInfo.time
                )
            }
            else -> error("The passed id($eventId) cannot be converted " +
                    "to DeviceEvent.AttemptUnlockDevice")
        }
    }

    //Id

    private suspend fun DeviceEvent.deviceEventToId(): Short {
        return when (this) {
            is DeviceEvent.AttemptUnlockDevice -> 0
            is DeviceEvent.AppOpen -> 1
            is DeviceEvent.DeviceLaunch -> 2
        }
    }
    //

    private suspend fun DeviceEvent.AttemptUnlockDevice.lockEventToId(): Short {
        return when (this) {
            is DeviceEvent.AttemptUnlockDevice.Failed -> 0
            is DeviceEvent.AttemptUnlockDevice.Succeeded -> 1
        }
    }
}