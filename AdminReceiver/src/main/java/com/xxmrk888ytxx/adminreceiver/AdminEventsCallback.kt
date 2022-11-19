package com.xxmrk888ytxx.adminreceiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

/**
 * [Ru]
 * Данный интерфейс предстовляет, колбэки для метотов аналогичных в [DeviceAdminReceiver]
 * и вызывает аналогичные по названию методы
 * [En]
 * This interface represents callbacks for methods similar to [DeviceAdminReceiver]
 * and calls methods similar in name
 */
interface AdminEventsCallback {
    fun onAdminEnabled()
    fun onAdminDisabled()
    fun onPasswordFailed(currentFailedPasswordAttempts:Int)
    fun onPasswordSucceeded()
    fun onReceive(context:Context,intent:Intent)
}