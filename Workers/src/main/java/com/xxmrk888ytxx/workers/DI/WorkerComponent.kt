package com.xxmrk888ytxx.workers.DI

import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.NotificationAppManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import dagger.Component
import dagger.Lazy

@Component(
    dependencies = [
        WorkerComponentDeps::class
    ]
)
@WorkerScope
internal interface WorkerComponent {
    @dagger.Component.Factory
    interface Factory {
        fun create(workerComponentDeps: WorkerComponentDeps): WorkerComponent
    }

    val telegramRepositoryFactory: Lazy<TelegramRepositoryFactory>

    val cameraManager: Lazy<CameraManager>

    val permissionsManager: Lazy<PermissionsManager>

    val appStateChanger: Lazy<AppStateChanger>

    val notificationAppManager: Lazy<NotificationAppManager>

    val maxStorageReportUseCase: Lazy<MaxStorageReportUseCase>

    val maxTimeStorageReportUseCase: Lazy<MaxTimeStorageReportUseCase>

    val deviceEventRepository: Lazy<DeviceEventRepository>
}