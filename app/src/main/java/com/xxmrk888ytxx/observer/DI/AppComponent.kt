package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.adutils.AdAppManager
import com.xxmrk888ytxx.bootreceiver.BootCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListenerManager
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.DI.module.*
import com.xxmrk888ytxx.observer.domain.NotificationAppManager.NotificationAppManager
import com.xxmrk888ytxx.observer.presentation.MainActivity
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
        ImageProviderModule::class,
        HandleEventModule::class,
        TrackedAppRepositoryModule::class,
        AppOpenConfigManagerModule::class,
        AppStateManagerModule::class,
        PermissionsManagerModule::class,
        NotificationAppManagerModule::class,
        AppPasswordManagerModule::class,
        BiometricAuthorizationManagerModule::class,
        BootCallbackModule::class,
        BootDeviceTrackedConfigManagerModule::class,
        RemoveAppManagerModule::class,
        AdAppManagerModule::class,
        BillingManagerModule::class,
        PurchaseListenerManagerModule::class,
        LocalizationManagerModule::class,
        DialogShowStateManagerModule::class,
        TimeLimitManagersModule::class,
        DeviceEventRepositoryModule::class
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

    val bootCallback : Provider<BootCallback>

    val appStateProvider: AppStateProvider

    val appStateChanger: AppStateChanger

    val permissionsManager:PermissionsManager

    val notificationAppManager: NotificationAppManager

    override val cameraManager:CameraManager

    val adAppManager:AdAppManager

    val purchaseListenerManager: PurchaseListenerManager
}