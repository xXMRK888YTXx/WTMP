package com.xxmrk888ytxx.observer.domain.AdminDeviceController

import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class AdminDeviceController @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository
) : AdminEventsCallback {
    override fun onAdminEnabled() {
        logcatMessageD("onAdminEnabled")
    }

    override fun onAdminDisabled() {
        logcatMessageD("onAdminDisabled")
    }

    override fun onPasswordFailed(currentFailedPasswordAttempts: Int) {
        ApplicationScope.launch {
            deviceEventRepository.addEvent(DeviceEvent.AttemptUnlockDevice.Failed(
                0,System.currentTimeMillis()
            ))
        }
    }

    override fun onPasswordSucceeded() {
        ApplicationScope.launch {
            deviceEventRepository.addEvent(DeviceEvent.AttemptUnlockDevice.Succeeded(
                0,System.currentTimeMillis()
            ))
        }
    }

    override fun onReceive(context: Context, intent: Intent) {}
}