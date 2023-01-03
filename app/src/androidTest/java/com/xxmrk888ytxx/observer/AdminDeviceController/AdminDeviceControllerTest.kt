package com.xxmrk888ytxx.observer.AdminDeviceController

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.AdminDeviceController.AdminDeviceController
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminDeviceControllerTest {
    private val deviceEventRepository:DeviceEventRepository = mockk(relaxed = true)
    private val failedUnlockTrackedConfigProvider: FailedUnlockTrackedConfigProvider = mockk(relaxed = true)
    private val handleEventUseCase: HandleEventUseCase = mockk(relaxed = true)
    private val appStateProvider: AppStateProvider = mockk(relaxed = true)
    private val adminEventsCallback: AdminEventsCallback =
        AdminDeviceController(deviceEventRepository,
            failedUnlockTrackedConfigProvider,
            handleEventUseCase,
            appStateProvider,
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true),
        )

    @Test
    fun callAdminControllerIfTrackedFailedUnlockOffExpectEventNotTracked() = runBlocking {
        val config: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        config.emit(FailedUnlockTrackedConfig(
            isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns config
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(0)

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

    @Test
    fun callMethodButAppStateIsFalseExpectEventNotAdd() = runBlocking {
        val flow: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        flow.emit(FailedUnlockTrackedConfig(
            isTracked = true,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(0)

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

    @Test
    fun callTrackerExpectTheySendParams() = runBlocking {
        val flow: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)

        flow.emit(FailedUnlockTrackedConfig(
            isTracked = true,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(0)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }
}