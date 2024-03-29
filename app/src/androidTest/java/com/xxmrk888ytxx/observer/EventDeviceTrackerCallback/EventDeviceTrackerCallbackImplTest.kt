package com.xxmrk888ytxx.observer.EventDeviceTrackerCallback

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.IsNowWorkTimeCheckUseCase
import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback.EventDeviceTrackerCallbackImpl
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

class EventDeviceTrackerCallbackImplTest {

    private val deviceEventRepository:DeviceEventRepository = mockk(relaxed = true)
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider = mockk(relaxed = true)
    private val handleEventUseCase: HandleEventUseCase = mockk(relaxed = true)
    private val appStateProvider = mockk<AppStateProvider>(relaxed = true)
    private val trackedAppRepository:TrackedAppRepository = mockk(relaxed = true)
    private val appOpenConfig:AppOpenConfigProvider = mockk(relaxed = true)
    private val timeOperationLimitManager: TimeOperationLimitManager<Nothing> = mockk(relaxed = true)
    private val isNowWorkTimeCheckUseCase: IsNowWorkTimeCheckUseCase = mockk(relaxed = true)

    private val eventDeviceTrackerCallback
    :EventDeviceTrackerCallback = EventDeviceTrackerCallbackImpl(
        deviceEventRepository, succeededUnlockTrackedConfigProvider, handleEventUseCase,appOpenConfig,
        trackedAppRepository,mockk(relaxed = true),appStateProvider,mockk(relaxed = true),
        mockk(relaxed = true),mockk(relaxed = true),timeOperationLimitManager,
        mockk(relaxed = true),isNowWorkTimeCheckUseCase
    )

    @Before
    fun init() {
        val list = MutableStateFlow(listOf("test"))
        coEvery { trackedAppRepository.getAllTrackedPackageNames() } returns list
        coEvery { timeOperationLimitManager.isLimitEnable(any()) } returns false
        coEvery { isNowWorkTimeCheckUseCase.execute() } returns true
    }

    @Test
    fun callAdminControllerIfTrackedFailedUnlockOffExpectEventNotTracked() = runBlocking {
        val config: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        config.emit(SucceededUnlockTrackedConfig(
            isTracked = false,
            timeOperationLimit = 0,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns config
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onScreenOn()

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

    @Test
    fun callTrackerExpectTheySendParams() = runBlocking {
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)

        flow.emit(SucceededUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = 0,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onScreenOn()

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callMethodButAppStateIsFalseExpectEventNotAdd() = runBlocking{
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        flow.emit(SucceededUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = 0,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onScreenOn()

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callOnOpenAppChangedExpectAddEvent() = runBlocking {
        val flow: MutableSharedFlow<AppOpenConfig> = MutableStateFlow(
            AppOpenConfig(
                isTracked = true,
                timeOperationLimit = 0,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
            )
        )
        val appState = MutableStateFlow<Boolean>(true)

        coEvery { appOpenConfig.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onOpenAppChanged("test")
        delay(1000)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callOnOpenAppChangedButAppStateFalseExpectNotAddEvent() = runBlocking {
        val flow: MutableSharedFlow<AppOpenConfig> = MutableStateFlow(
            AppOpenConfig(
                isTracked = true,
                timeOperationLimit = 0,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
            )
        )
        val appState = MutableStateFlow<Boolean>(false)

        coEvery { appOpenConfig.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onOpenAppChanged("test")

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callOnOpenAppChangedButAppNotTrackedExpectNotAddEvent() = runBlocking {
        val flow: MutableSharedFlow<AppOpenConfig> = MutableStateFlow(
            AppOpenConfig(
                isTracked = true,
                timeOperationLimit = 0,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false,
            )
        )
        val appState = MutableStateFlow<Boolean>(true)

        coEvery { appOpenConfig.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onOpenAppChanged("test2")

        coVerify(exactly = 0) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 0) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun setTimeLimitAndCallMethodExpectInformationWillBeSaveIfLimitTimeOut() = runBlocking {
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(true)
        val timeLimit = 100

        flow.emit( SucceededUnlockTrackedConfig(
            isTracked = true,
            timeOperationLimit = timeLimit,
            makePhoto = true,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false
        ))

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onScreenOn()
        coEvery { timeOperationLimitManager.isLimitEnable(timeLimit) } returns true
        eventDeviceTrackerCallback.onScreenOn()
        eventDeviceTrackerCallback.onScreenOn()
        coEvery { timeOperationLimitManager.isLimitEnable(timeLimit) } returns false
        eventDeviceTrackerCallback.onScreenOn()
        delay(50)

        coVerify(exactly = 2) { timeOperationLimitManager.enableLimit(timeLimit) }
        coVerify(exactly = 4) { timeOperationLimitManager.isLimitEnable(timeLimit) }
        coVerify(exactly = 2) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 2) { handleEventUseCase.execute(
            any(),true,true,false,any()
        ) }
    }

    @Test
    fun callOnOpenAppChangedWithNowWorkTimeCheckUseCaseTrueAndFalseExpectCallbackWorkOnlyOneAttempt() = runBlocking {
        val flow: MutableSharedFlow<AppOpenConfig> = MutableStateFlow(
            AppOpenConfig(
                isTracked = true,
                timeOperationLimit = 0,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
            )
        )
        val appState = MutableStateFlow<Boolean>(true)

        coEvery { appOpenConfig.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        coEvery { isNowWorkTimeCheckUseCase.execute() } returns true

        eventDeviceTrackerCallback.onOpenAppChanged("test")
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }

        coEvery { isNowWorkTimeCheckUseCase.execute() } returns false
        eventDeviceTrackerCallback.onOpenAppChanged("test")
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

    @Test
    fun callOnScreenOnWithNowWorkTimeCheckUseCaseTrueAndFalseExpectCallbackWorkOnlyOneAttempt() = runBlocking {
        val flow: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableStateFlow(
            SucceededUnlockTrackedConfig(
                isTracked = true,
                timeOperationLimit = 0,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
            )
        )
        val appState = MutableStateFlow<Boolean>(true)

        coEvery { succeededUnlockTrackedConfigProvider.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        coEvery { isNowWorkTimeCheckUseCase.execute() } returns true

        eventDeviceTrackerCallback.onScreenOn()
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }

        coEvery { isNowWorkTimeCheckUseCase.execute() } returns false
        eventDeviceTrackerCallback.onScreenOn()
        delay(50)

        coVerify(exactly = 1) { deviceEventRepository.addEvent(any()) }
        coVerify(exactly = 1) { handleEventUseCase.execute(any(),any(),any(),any(),any()) }
    }

}