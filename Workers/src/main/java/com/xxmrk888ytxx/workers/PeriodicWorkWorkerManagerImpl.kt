package com.xxmrk888ytxx.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PeriodicWorkWorkerManager
import com.xxmrk888ytxx.workers.Workers.ServiceWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PeriodicWorkWorkerManagerImpl @Inject constructor(
    private val context: Context
) : PeriodicWorkWorkerManager {

    override fun enableServiceWorker() {
        val worker = PeriodicWorkRequestBuilder<ServiceWorker>(3, TimeUnit.HOURS)
            .addTag(SERVICE_WORKER_NAME)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SERVICE_WORKER_NAME,
            ExistingPeriodicWorkPolicy.UPDATE, worker
        )
    }

    override fun disableServiceWorker() {
        WorkManager.getInstance(context).cancelAllWorkByTag(SERVICE_WORKER_NAME)
    }

    companion object {
        const val SERVICE_WORKER_NAME = "WTMPServiceWorker"
    }
}