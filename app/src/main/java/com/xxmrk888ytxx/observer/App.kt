package com.xxmrk888ytxx.observer

import android.app.Application
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.bootreceiver.BootCallback
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import com.xxmrk888ytxx.coredeps.Exceptions.DepsProviderNotFoundDeps
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.DI.AppComponent
import com.xxmrk888ytxx.observer.DI.DaggerAppComponent
import kotlin.reflect.KClass

class App : Application(),DepsProvider,ApplicationInfoProvider {

    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this,this)
    }

    private val depsProviders:List<Any> by lazy {
        listOf(appComponent,adminEventsCallback,eventDeviceTrackerCallback,bootCallback)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <DEPS : Any> provide(classType: KClass<DEPS>) : DEPS {

        depsProviders.forEach {
            if(classType.isInstance(it)) return it as DEPS
        }

        throw DepsProviderNotFoundDeps("DepsProvider cant provide ${classType.simpleName}")
    }

    override val applicationVersion: String = BuildConfig.VERSION_NAME

    private val adminEventsCallback: AdminEventsCallback
        get() = appComponent.adminEventsCallback.get()

    private val eventDeviceTrackerCallback: EventDeviceTrackerCallback
        get() = appComponent.eventDeviceTrackerCallback.get()

    private val bootCallback : BootCallback
        get() = appComponent.bootCallback.get()
}