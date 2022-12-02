package com.xxmrk888ytxx.adminreceiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.UserHandle
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

class AdminReceiver : DeviceAdminReceiver() {

    private val Context.adminCallBack : AdminEventsCallback
        get() {
            return this.getDepsByApplication()
        }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        context.adminCallBack.onReceive(context, intent)
    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        context.adminCallBack.onAdminEnabled()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        context.adminCallBack.onAdminDisabled()
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onPasswordFailed(context: Context, intent: Intent) {
        super.onPasswordFailed(context, intent)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.adminCallBack.onPasswordFailed(getManager(context).currentFailedPasswordAttempts)
        }
    }

    override fun onPasswordFailed(context: Context, intent: Intent, user: UserHandle) {
        super.onPasswordFailed(context, intent, user)
        context.adminCallBack.onPasswordFailed(getManager(context).currentFailedPasswordAttempts)

    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onPasswordSucceeded(context: Context, intent: Intent) {
        super.onPasswordSucceeded(context, intent)
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.adminCallBack.onPasswordSucceeded()
        }
    }

    override fun onPasswordSucceeded(context: Context, intent: Intent, user: UserHandle) {
        super.onPasswordSucceeded(context, intent, user)
        context.adminCallBack.onPasswordSucceeded()
    }
}