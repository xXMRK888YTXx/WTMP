package com.xxmrk888ytxx.observer.AdminDeviceController

import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.observer.DI.AppScope
import javax.inject.Inject


internal class AdminDeviceController @Inject constructor() : AdminEventsCallback {
    override fun onAdminEnabled() {
        logcatMessageD("onAdminEnabled")
    }

    override fun onAdminDisabled() {
        logcatMessageD("onAdminDisabled")
    }

    override fun onPasswordFailed(currentFailedPasswordAttempts: Int) {
        logcatMessageD("onPasswordFailed: $currentFailedPasswordAttempts")
    }

    override fun onPasswordSucceeded() {
        logcatMessageD("onPasswordSucceeded")
    }

    override fun onReceive(context: Context, intent: Intent) {

    }
}