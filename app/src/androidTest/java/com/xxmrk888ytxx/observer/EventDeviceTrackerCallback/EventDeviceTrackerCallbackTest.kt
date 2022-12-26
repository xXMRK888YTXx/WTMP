package com.xxmrk888ytxx.observer.EventDeviceTrackerCallback

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback.EventDeviceTrackerCallbackImpl
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class EventDeviceTrackerCallbackTest {

    private val deviceEventRepository:DeviceEventRepository = mockk(relaxed = true)
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider = mockk(relaxed = true)
    private val handleEventUseCase: HandleEventUseCase = mockk(relaxed = true)

    private val eventDeviceTrackerCallback
    :EventDeviceTrackerCallback = EventDeviceTrackerCallbackImpl(
        deviceEventRepository, succeededUnlockTrackedConfigProvider, handleEventUseCase
    )

    @Test
    fun callAdminControllerIfTrackedFailedUnlockOffExpectEventNotTracked() = runBlocking {
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)

        flow.emit(SucceededUnlockTrackedConfig(
            isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow

        eventDeviceTrackerCallback.onScreenOn()

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

    @Test
    fun callTrackerExpectTheySendParams() = runBlocking {
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)

        flow.emit(SucceededUnlockTrackedConfig(
            isTracked = true,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow

        eventDeviceTrackerCallback.onScreenOn()

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

}