package com.xxmrk888ytxx.observer.DI

import android.content.Context
import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.bootreceiver.BootCallback
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ApplicationInfoProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.NotificationAppManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListenerManager
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.DI.module.AdminReceiverModule
import com.xxmrk888ytxx.observer.DI.module.AppOpenChangedCallbackModule
import com.xxmrk888ytxx.observer.DI.module.AppOpenConfigManagerModule
import com.xxmrk888ytxx.observer.DI.module.AppPasswordManagerModule
import com.xxmrk888ytxx.observer.DI.module.AppStateManagerModule
import com.xxmrk888ytxx.observer.DI.module.BillingManagerModule
import com.xxmrk888ytxx.observer.DI.module.BiometricAuthorizationManagerModule
import com.xxmrk888ytxx.observer.DI.module.BootCallbackModule
import com.xxmrk888ytxx.observer.DI.module.BootDeviceTrackedConfigManagerModule
import com.xxmrk888ytxx.observer.DI.module.CameraModule
import com.xxmrk888ytxx.observer.DI.module.CryptoManagerModule
import com.xxmrk888ytxx.observer.DI.module.DatabaseModule
import com.xxmrk888ytxx.observer.DI.module.DeviceEventRepositoryModule
import com.xxmrk888ytxx.observer.DI.module.DialogShowStateManagerModule
import com.xxmrk888ytxx.observer.DI.module.FailedUnlockTrackedConfigModule
import com.xxmrk888ytxx.observer.DI.module.HandleEventModule
import com.xxmrk888ytxx.observer.DI.module.ImageProviderModule
import com.xxmrk888ytxx.observer.DI.module.ImageRepositoryModule
import com.xxmrk888ytxx.observer.DI.module.IsNowWorkTimeCheckUseCaseModule
import com.xxmrk888ytxx.observer.DI.module.LocalizationManagerModule
import com.xxmrk888ytxx.observer.DI.module.MaxStorageReportUseCaseModule
import com.xxmrk888ytxx.observer.DI.module.MaxTimeStorageReportUseCaseModule
import com.xxmrk888ytxx.observer.DI.module.NotificationAppManagerModule
import com.xxmrk888ytxx.observer.DI.module.PackageInfoProviderModule
import com.xxmrk888ytxx.observer.DI.module.PermissionsManagerModule
import com.xxmrk888ytxx.observer.DI.module.PurchaseListenerManagerModule
import com.xxmrk888ytxx.observer.DI.module.RemoveAppManagerModule
import com.xxmrk888ytxx.observer.DI.module.RemoveEventImageUseCaseModule
import com.xxmrk888ytxx.observer.DI.module.ResourcesProviderModule
import com.xxmrk888ytxx.observer.DI.module.StorageConfigManagerModule
import com.xxmrk888ytxx.observer.DI.module.SucceededUnlockTrackedConfigModule
import com.xxmrk888ytxx.observer.DI.module.TelegramConfigModule
import com.xxmrk888ytxx.observer.DI.module.TelegramRepositoryFactoryModule
import com.xxmrk888ytxx.observer.DI.module.TimeLimitManagersModule
import com.xxmrk888ytxx.observer.DI.module.ToastManagerModule
import com.xxmrk888ytxx.observer.DI.module.TrackedAppRepositoryModule
import com.xxmrk888ytxx.observer.DI.module.WorkTimeConfigManagerModule
import com.xxmrk888ytxx.observer.DI.module.WorkerManagerModule
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
        BillingManagerModule::class,
        PurchaseListenerManagerModule::class,
        LocalizationManagerModule::class,
        DialogShowStateManagerModule::class,
        TimeLimitManagersModule::class,
        DeviceEventRepositoryModule::class,
        RemoveEventImageUseCaseModule::class,
        StorageConfigManagerModule::class,
        MaxTimeStorageReportUseCaseModule::class,
        MaxStorageReportUseCaseModule::class,
        WorkTimeConfigManagerModule::class,
        IsNowWorkTimeCheckUseCaseModule::class
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

    override val appStateChanger: AppStateChanger

    override val permissionsManager:PermissionsManager

    override val notificationAppManager: NotificationAppManager

    override val cameraManager:CameraManager
    val purchaseListenerManager: PurchaseListenerManager
}