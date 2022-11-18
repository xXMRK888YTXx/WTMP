package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.observer.DI.module.AdminReceiverModule
import com.xxmrk888ytxx.observer.DI.module.ToastManagerModule
import com.xxmrk888ytxx.observer.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@AppScope
@Component(
    modules = [
        ToastManagerModule::class,
        AdminReceiverModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context:Context) : AppComponent
    }
    val adminEventsCallback:AdminEventsCallback
}

@Scope
annotation class AppScope