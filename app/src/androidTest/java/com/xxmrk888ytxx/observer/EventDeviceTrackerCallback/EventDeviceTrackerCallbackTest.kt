package com.xxmrk888ytxx.observer.EventDeviceTrackerCallback

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback.EventDeviceTrackerCallbackImpl
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class EventDeviceTrackerCallbackTest {

    private val deviceEventRepository:DeviceEventRepository = mockk(relaxed = true)
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider = mockk(relaxed = true)
    private val handleEventUseCase: HandleEventUseCase = mockk(relaxed = true)
    private val appStateProvider = mockk<AppStateProvider>(relaxed = true)
    private val trackedAppRepository:TrackedAppRepository = mockk(relaxed = true)
    private val appOpenConfig:AppOpenConfigProvider = mockk(relaxed = true)

    private val eventDeviceTrackerCallback
    :EventDeviceTrackerCallback = EventDeviceTrackerCallbackImpl(
        deviceEventRepository, succeededUnlockTrackedConfigProvider, handleEventUseCase,appOpenConfig,
        trackedAppRepository,mockk(relaxed = true),appStateProvider
    )

    @Before
    fun init() {
        val list = MutableStateFlow(listOf("test"))
        coEvery { trackedAppRepository.getAllTrackedPackageNames() } returns list
    }

    @Test
    fun callAdminControllerIfTrackedFailedUnlockOffExpectEventNotTracked() = runBlocking {
        val config: MutableSharedFlow<SucceededUnlockTrackedConfig> = MutableSharedFlow(1,1)
        val appState = MutableStateFlow<Boolean>(false)

        config.emit(SucceededUnlockTrackedConfig(
            isTracked = false,
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
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
            )
        )
        val appState = MutableStateFlow<Boolean>(true)

        coEvery { appOpenConfig.config } returns flow
        coEvery { appStateProvider.isAppEnable } returns appState

        eventDeviceTrackerCallback.onOpenAppChanged("test")

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
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = false
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

}