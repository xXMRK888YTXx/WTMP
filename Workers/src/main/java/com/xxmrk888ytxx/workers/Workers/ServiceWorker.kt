package com.xxmrk888ytxx.workers.Workers

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.NotificationAppManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import com.xxmrk888ytxx.coredeps.isAllPermissionsGranted
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import com.xxmrk888ytxx.workers.DI.WorkerComponent

class ServiceWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val component: WorkerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(context.getDepsByApplication())
    }

    val permissionsManager: PermissionsManager by lazy { component.permissionsManager.get() }
    val notificationAppManager: NotificationAppManager by lazy { component.notificationAppManager.get() }

    val appStateChanger: AppStateChanger by lazy { component.appStateChanger.get() }

    val maxTimeStorageReportUseCase: MaxTimeStorageReportUseCase by lazy { component.maxTimeStorageReportUseCase.get() }

    val maxStorageReportUseCase: MaxStorageReportUseCase by lazy { component.maxStorageReportUseCase.get() }

    val deviceEventRepository: DeviceEventRepository by lazy { component.deviceEventRepository.get() }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            FOREGROUND_NOTIFICATION_ID, notificationAppManager.notificationForServiceWorker,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE else 0
        )
    }


    override suspend fun doWork(): Result = try {
        checkPermissionAndNotifyUserIfPermissionNotGranted()
        checkStorageForOutdatedRecords()
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }

    private suspend fun checkPermissionAndNotifyUserIfPermissionNotGranted() {
        if (permissionsManager.isAllPermissionsGranted) return
        if (!permissionsManager.isNotificationPermissionGranted()) notificationAppManager.sendSomePermissionWasWithdrawnNotification()
    }

    private suspend fun checkStorageForOutdatedRecords() {
        maxTimeStorageReportUseCase.execute(deviceEventRepository)
        maxStorageReportUseCase.execute(deviceEventRepository)
    }

    companion object {
        const val FOREGROUND_NOTIFICATION_ID = 1235
    }
}