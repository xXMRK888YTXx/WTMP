package com.xxmrk888ytxx.adminreceiver

import android.content.Context
import android.content.Intent

interface AdminEventsCallback {
    fun onAdminEnabled()
    fun onAdminDisabled()
    fun onPasswordFailed(currentFailedPasswordAttempts:Int)
    fun onPasswordSucceeded()
    fun onReceive(context:Context,intent:Intent)
}