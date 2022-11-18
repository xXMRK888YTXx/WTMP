package com.xxmrk888ytxx.observer

import android.app.Application
import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.adminreceiver.AdminCallbackProvider
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageE
import com.xxmrk888ytxx.observer.DI.AppComponent
import com.xxmrk888ytxx.observer.DI.DaggerAppComponent

class App : Application(),AdminCallbackProvider {
    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
    override val adminEventsCallback: AdminEventsCallback
        get() = appComponent.adminEventsCallback
}