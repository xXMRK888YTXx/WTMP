package com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback

import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerParams
import com.xxmrk888ytxx.observer.domain.NotificationAppManager.NotificationAppManager
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class EventDeviceTrackerCallbackImpl @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository,
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider,
    private val handleEventUseCase: HandleEventUseCase,
    private val appOpenConfig: AppOpenConfigProvider,
    private val trackedAppRepository: TrackedAppRepository,
    private val packageInfoProvider: PackageInfoProvider,
    private val appStateProvider: AppStateProvider,
    private val notificationAppManager: NotificationAppManager,
    private val appStateChanger: AppStateChanger
): EventDeviceTrackerCallback {

    override val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().setIgnoreList(ignoreList).build()

    override fun onOpenAppChanged(packageName: String) {
        ApplicationScope.launch {
            val config by lazy {
                appOpenConfig.config
            }

            if((!config.first().isTracked||!appStateProvider.isAppEnable.first())
                ||!isTrackedPackageName(packageName)) return@launch

            val eventId = deviceEventRepository.addEvent(
                DeviceEvent.AppOpen(0,null,packageName,
                    null,System.currentTimeMillis())
            )

            val appName = packageInfoProvider.getAppName(packageName) ?: packageName

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                "Приложение $appName было открыто."
            )
        }
    }

    private suspend fun isTrackedPackageName(packageName: String): Boolean {
        return packageName in trackedAppRepository.getAllTrackedPackageNames().first()
    }

    override fun onScreenOn() {
        ApplicationScope.launch {

            val config by lazy {
                succeededUnlockTrackedConfigProvider.config
            }

            if(!config.first().isTracked||!appStateProvider.isAppEnable.first()) return@launch

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

    override fun onServiceDestroy() {
        ApplicationScope.launch(Dispatchers.IO) {
            if(!appStateProvider.isAppEnable.first()) return@launch
            notificationAppManager.sendAccessibilityPermissionWithdrawnNotification()
            appStateChanger.updateAppState(false)
        }
    }

    private val ignoreList:List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}