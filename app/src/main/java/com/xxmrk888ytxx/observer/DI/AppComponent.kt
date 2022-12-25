package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.DI.module.*
import com.xxmrk888ytxx.observer.MainActivity
import com.xxmrk888ytxx.workers.DI.WorkerComponentDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider

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
        ResourcesProviderModule::class,
        CryptoManagerModule::class,
        TelegramConfigModule::class,
        DatabaseModule::class,
        AppOpenChangedCallbackModule::class,
        FailedUnlockTrackedConfigModule::class,
        SucceededUnlockTrackedConfigModule::class,
        ImageRepositoryModule::class,
        ImageProviderModule::class
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

    val adminEventsCallback:Provider<AdminEventsCallback>

    val eventDeviceTrackerCallback: Provider<EventDeviceTrackerCallback>

    override val cameraManager:CameraManager
}