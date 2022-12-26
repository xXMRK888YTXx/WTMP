package com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback

import android.util.Log
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerParams
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDeviceTrackerCallbackImpl @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository,
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider,
    private val handleEventUseCase: HandleEventUseCase
): EventDeviceTrackerCallback {
    override val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().setIgnoreList(ignoreList).build()

    override fun onOpenAppChanged(packageName: String) {
        Log.d("MyLog",packageName)
    }

    override fun onScreenOn() {
        ApplicationScope.launch {

            val config by lazy {
                succeededUnlockTrackedConfigProvider.config
            }

            if(!config.first().isTracked) return@launch

            val eventId =  deviceEventRepository.addEvent(
                DeviceEvent.AttemptUnlockDevice.Succeeded(0,System.currentTimeMillis())
            )

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                "Устройтво было разблокировано"
            )
        }
    }

    private val ignoreList:List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}