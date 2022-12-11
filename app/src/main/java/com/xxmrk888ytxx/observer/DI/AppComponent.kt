package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.observer.DI.module.*
import com.xxmrk888ytxx.observer.MainActivity
import com.xxmrk888ytxx.workers.DI.WorkerComponentDeps
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        ToastManagerModule::class,
        AdminReceiverModule::class,
        CameraModule::class,
        UserActivityStatsModule::class,
        TelegramRepositoryFactoryModule::class,
        WorkerManagerModule::class,
        PackageInfoProviderModule::class,
        ResourcesProviderModule::class
    ]
)
interface AppComponent : WorkerComponentDeps {
    fun inject(mainActivity: MainActivity)
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context:Context,
            @BindsInstance applicationInfoProvider: ApplicationInfoProvider
        ) : AppComponent
    }
    val adminEventsCallback:AdminEventsCallback
}