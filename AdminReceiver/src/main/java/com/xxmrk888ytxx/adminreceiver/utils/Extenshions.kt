package com.xxmrk888ytxx.adminreceiver.utils

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminCallbackProvider
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.adminreceiver.utils.Exceptions.ApplicationNotImplementationAdminProvider

internal val Context.adminCallBack : AdminEventsCallback
    get() {
        if(this is AdminCallbackProvider) return this.adminEventsCallback
        when(this.applicationContext) {
            is AdminCallbackProvider -> return (this.applicationContext
                    as AdminCallbackProvider).adminEventsCallback
            else -> throw ApplicationNotImplementationAdminProvider("The class that extends the application class" +
                    " must implement AdminCallbackProvider")
        }
    }