package com.xxmrk888ytxx.workers.DI

import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.NotificationAppManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase

/**
 * [Documentation]
 *
 * This interface defines the dependencies required for the worker component.
 * It acts as a bridge, providing access to essential application-level services
 * and managers that background workers need to perform their tasks.
 *
 * This approach follows the Dependency Inversion Principle, allowing the worker module
 * to depend on abstractions rather than concrete implementations, which are provided
 * by the application's main dependency graph.
 *
 * @property telegramRepositoryFactory Factory for creating instances of [TelegramRepository].
 * @property cameraManager Manages camera operations.
 * @property permissionsManager Handles runtime permission requests and checks.
 * @property appStateChanger Interface for modifying the application's overall state.
 * @property notificationAppManager Service for creating and managing application notifications.
 */
interface WorkerComponentDeps {

    val telegramRepositoryFactory: TelegramRepositoryFactory

    val cameraManager: CameraManager

    val permissionsManager: PermissionsManager

    val appStateChanger: AppStateChanger

    val notificationAppManager: NotificationAppManager

    val maxStorageReportUseCase: MaxStorageReportUseCase

    val maxTimeStorageReportUseCase: MaxTimeStorageReportUseCase

    val deviceEventRepository: DeviceEventRepository

}