package com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback

import android.annotation.SuppressLint
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.AppOpenLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.SucceededUnlockLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerParams
import com.xxmrk888ytxx.observer.R
import com.xxmrk888ytxx.observer.domain.NotificationAppManager.NotificationAppManager
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
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
    private val appStateChanger: AppStateChanger,
    private val resourcesProvider: ResourcesProvider,
    @SucceededUnlockLimitManagerQualifier
    private val timeOperationLimitManagerForScreenOn: TimeOperationLimitManager<Nothing>,
    @AppOpenLimitManagerQualifier
    private val timeOperationLimitManagerAppOpen: TimeOperationLimitManager<String>,
) : EventDeviceTrackerCallback {

    override val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().setIgnoreList(ignoreList).build()

    @SuppressLint("ResourceType")
    override fun onOpenAppChanged(packageName: String) {
        ApplicationScope.launch {
            val config by lazy {
                appOpenConfig.config
            }

            if ((!config.first().isTracked || !appStateProvider.isAppEnable.first())
                || !isTrackedPackageName(packageName) || timeOperationLimitManagerAppOpen.isLimitEnable(
                    config.first().timeOperationLimit,
                    packageName
                )
            ) return@launch

            if (config.first().timeOperationLimit != 0)
                timeOperationLimitManagerAppOpen.enableLimit(
                    config.first().timeOperationLimit,
                    packageName
                )

            val eventId = deviceEventRepository.addEvent(
                DeviceEvent.AppOpen(
                    0, null, packageName,
                    null, System.currentTimeMillis()
                )
            )

            val appName = packageInfoProvider.getAppName(packageName) ?: packageName

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                "${resourcesProvider.getString(R.string.Application)} " +
                        appName +
                        " ${resourcesProvider.getString(R.string.was_open)}"
            )
        }
    }

    private suspend fun isTrackedPackageName(packageName: String): Boolean {
        return packageName in trackedAppRepository.getAllTrackedPackageNames().first()
    }

    @SuppressLint("ResourceType")
    override fun onScreenOn() {
        ApplicationScope.launch {

            val config by lazy {
                succeededUnlockTrackedConfigProvider.config
            }

            if (!config.first().isTracked || !appStateProvider.isAppEnable.first()
                || timeOperationLimitManagerForScreenOn.isLimitEnable(config.first().timeOperationLimit)
            ) return@launch

            if (config.first().timeOperationLimit != 0)
                timeOperationLimitManagerForScreenOn.enableLimit(config.first().timeOperationLimit)

            val eventId = deviceEventRepository.addEvent(
                DeviceEvent.AttemptUnlockDevice.Succeeded(0, System.currentTimeMillis())
            )

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                resourcesProvider.getString(R.string.Device_unlocked)
            )
        }
    }

    override fun onServiceDestroy() {}

    private val ignoreList: List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}