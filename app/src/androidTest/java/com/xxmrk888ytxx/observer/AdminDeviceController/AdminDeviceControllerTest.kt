package com.xxmrk888ytxx.observer.AdminDeviceController

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.IsNowWorkTimeCheckUseCase
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.AdminDeviceController.AdminDeviceController
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase.HandleEventUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminDeviceControllerTest {
    private val deviceEventRepository:DeviceEventRepository = mockk(relaxed = true)
    private val failedUnlockTrackedConfigProvider: FailedUnlockTrackedConfigProvider = mockk(relaxed = true)
    private val handleEventUseCase: HandleEventUseCase = mockk(relaxed = true)
    private val appStateProvider: AppStateProvider = mockk(relaxed = true)
    private val timeOperationLimitManager:TimeOperationLimitManager<Nothing> = mockk(relaxed = true)
    private val isNowWorkTimeCheckUseCase: IsNowWorkTimeCheckUseCase = mockk(relaxed = true)
    private val adminEventsCallback: AdminEventsCallback =
        AdminDeviceController(deviceEventRepository,
            failedUnlockTrackedConfigProvider,
            handleEventUseCase,
            appStateProvider,
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true),
            timeOperationLimitManager,
            isNowWorkTimeCheckUseCase
        )

    @Before
    fun before() {
        coEvery { timeOperationLimitManager.isLimitEnable(any()) } returns false
        coEvery { isNowWorkTimeCheckUseCase.execute() } returns true
    }

    @Test
    fun callAdminControllerIfTrackedFailedUnlockOffExpectEventNotTracked() = runBlocking {
        val config: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        config.emit(FailedUnlockTrackedConfig(
            countFailedUnlockToTrigger = 1,
            timeOperationLimit = 0,
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
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
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
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(1)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun setCountFailedUnlockToTriggerParamsAndCallOnPasswordFailedExpectThisWorkOnlyAfterCountFailedUnlockToTrigger()
        = runBlocking {
        val flow: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)

        flow.emit(FailedUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 5,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(1)
        adminEventsCallback.onPasswordFailed(2)
        adminEventsCallback.onPasswordFailed(3)
        adminEventsCallback.onPasswordFailed(4)
        adminEventsCallback.onPasswordFailed(5)
        adminEventsCallback.onPasswordFailed(6)
        delay(50)

        coVerify(exactly = 2) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 2) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun setTimeLimitAndCallMethodExpectInformationWillBeSaveIfLimitTimeOut() = runBlocking {
        val flow: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)
        val timeLimit = 100

        flow.emit(FailedUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = timeLimit,
            countFailedUnlockToTrigger = 1,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        adminEventsCallback.onPasswordFailed(1)
        coEvery { timeOperationLimitManager.isLimitEnable(timeLimit) } returns true
        adminEventsCallback.onPasswordFailed(2)
        adminEventsCallback.onPasswordFailed(3)
        coEvery { timeOperationLimitManager.isLimitEnable(timeLimit) } returns false
        adminEventsCallback.onPasswordFailed(4)
        delay(50)

        coVerify(exactly = 4) { timeOperationLimitManager.isLimitEnable(timeLimit) }
        coVerify(exactly = 2) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 2) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callWithNowWorkTimeCheckUseCaseTrueAndFalseExpectCallbackWorkOnlyOneAttempt() = runBlocking {
        val flow: MutableSharedFlow<FailedUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)

        flow.emit(FailedUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 0,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { failedUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState
        coEvery { isNowWorkTimeCheckUseCase.execute() } returns true

        adminEventsCallback.onPasswordFailed(0)
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }

        coEvery { isNowWorkTimeCheckUseCase.execute() } returns false
        adminEventsCallback.onPasswordFailed(1)
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }

    }
}