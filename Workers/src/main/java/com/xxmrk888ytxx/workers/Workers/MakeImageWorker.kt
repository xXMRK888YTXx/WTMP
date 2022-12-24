package com.xxmrk888ytxx.workers.Workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MakeImageWorker(
    private val context: Context,
    private val params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val workerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(context.getDepsByApplication())
    }

    private val cameraManager by lazy {
        workerComponent.cameraManager
    }

    private val imagePath: File by lazy {
        val path = params.inputData.getString(ImagePath) ?: throw Exception("Not setup ImagePath " +
                "params")
        File(path)
    }

    private fun isHaveCameraPermission() : Boolean {
        return ContextCompat
            .checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }

    override suspend fun doWork(): Result {
        if(!isHaveCameraPermission()) throw Exception("Application haven't permission to camera." +
                "Request ${Manifest.permission.CAMERA} permission")
        ApplicationScope.launch(Dispatchers.IO) {
            cameraManager.createPhoto(
                imagePath,
                onErrorCreate = {
                    throw it
                }
            )
        }.join()
        return Result.success()
    }

    companion object {
        const val ImagePath = "ImagePath"
    }
}