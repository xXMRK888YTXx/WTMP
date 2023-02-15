package com.xxmrk888ytxx.observer.domain.AdminDeviceController

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.FailedUnlockLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.logcatMessageD
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.observer.R
import com.xxmrk888ytxx.observer.domain.NotificationAppManager.NotificationAppManager
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class AdminDeviceController @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository,
    private val failedUnlockTrackedConfigProvider: FailedUnlockTrackedConfigProvider,
    private val handleEventUseCase: HandleEventUseCase,
    private val appStateProvider: AppStateProvider,
    private val notificationAppManager: NotificationAppManager,
    private val appStateChanger: AppStateChanger,
    private val resourcesProvider: ResourcesProvider,
    @FailedUnlockLimitManagerQualifier
    private val timeOperationLimitManager: TimeOperationLimitManager<Nothing>,
) : AdminEventsCallback {

    override fun onAdminEnabled() {
        logcatMessageD("onAdminEnabled")
    }

    override fun onAdminDisabled() {
        ApplicationScope.launch(Dispatchers.IO) {
            if (!appStateProvider.isAppEnable.first()) return@launch
            notificationAppManager.sendAdminPermissionWithdrawnNotification()
            appStateChanger.updateAppState(false)
        }
    }

    @SuppressLint("ResourceType")
    override fun onPasswordFailed(currentFailedPasswordAttempts: Int) {
        ApplicationScope.launch {
            val config by lazy {
                failedUnlockTrackedConfigProvider.config
            }

            if (!config.first().isTracked ||
                !appStateProvider.isAppEnable.first() ||
                config.first().countFailedUnlockToTrigger > currentFailedPasswordAttempts ||
                timeOperationLimitManager.isLimitEnable(config.first().timeOperationLimit)
            ) return@launch

            if(config.first().timeOperationLimit != 0)
                timeOperationLimitManager.enableLimit(config.first().timeOperationLimit)

            val eventId = deviceEventRepository.addEvent(
                DeviceEvent.AttemptUnlockDevice.Failed(
                    0, System.currentTimeMillis()
                )
            )

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                resourcesProvider.getString(R.string.Failed_unlock)
            )


        }
    }

    override fun onPasswordSucceeded() {}

    override fun onReceive(context: Context, intent: Intent) {}
}