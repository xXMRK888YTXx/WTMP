package com.xxmrk888ytxx.observer.domain.AdminDeviceController

import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class AdminDeviceController @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository,
    private val succeededUnlockTrackedConfigProvider: SucceededUnlockTrackedConfigProvider,
    private val imageRepository: ImageRepository,
    private val workerManager: WorkerManager
) : AdminEventsCallback {
    override fun onAdminEnabled() {
        logcatMessageD("onAdminEnabled")
    }

    override fun onAdminDisabled() {
        logcatMessageD("onAdminDisabled")
    }

    override fun onPasswordFailed(currentFailedPasswordAttempts: Int) {
        ApplicationScope.launch {
            val eventId = deviceEventRepository.addEvent(DeviceEvent.AttemptUnlockDevice.Failed(
                0,System.currentTimeMillis()
            ))
            if(succeededUnlockTrackedConfigProvider.config.first().makePhoto) {
                workerManager.createImageWorker(imageRepository.getSaveImageFile(eventId).absolutePath)
            }
        }
    }

    override fun onPasswordSucceeded() {}

    override fun onReceive(context: Context, intent: Intent) {}
}