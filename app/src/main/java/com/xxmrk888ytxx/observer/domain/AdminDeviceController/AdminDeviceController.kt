package com.xxmrk888ytxx.observer.domain.AdminDeviceController

import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
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
    private val appStateChanger: AppStateChanger
) : AdminEventsCallback {
    override fun onAdminEnabled() {
        logcatMessageD("onAdminEnabled")
    }

    override fun onAdminDisabled() {
        ApplicationScope.launch(Dispatchers.IO) {
            if(!appStateProvider.isAppEnable.first()) return@launch
            notificationAppManager.sendAdminPermissionWithdrawnNotification()
            appStateChanger.updateAppState(false)
        }
    }

    override fun onPasswordFailed(currentFailedPasswordAttempts: Int) {
        ApplicationScope.launch {
            val config by lazy {
                failedUnlockTrackedConfigProvider.config
            }

            if(!config.first().isTracked||!appStateProvider.isAppEnable.first()) return@launch

            val eventId = deviceEventRepository.addEvent(DeviceEvent.AttemptUnlockDevice.Failed(
                0,System.currentTimeMillis()
            ))

            handleEventUseCase.execute(
                eventId,
                isSendTelegramMessage = config.first().notifyInTelegram,
                makePhoto = config.first().makePhoto,
                joinPhotoToTelegramNotify = config.first().joinPhotoToTelegramNotify,
                "Зафиксирована неудачная разблокировка устройтва."
            )



        }
    }

    override fun onPasswordSucceeded() {}

    override fun onReceive(context: Context, intent: Intent) {}
}