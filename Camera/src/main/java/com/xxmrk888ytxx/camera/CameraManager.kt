package com.xxmrk888ytxx.camera

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.io.File

class CameraManager(val context: Context) : LifecycleOwner {

    private fun onCreate() {
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    private fun onDestroy(cameraProvider: ProcessCameraProvider) {
        cameraProvider.unbindAll()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    @SuppressLint("RestrictedApi")
    fun createPhoto(
        outputFile: File,
        onSuccess:(outputFileResults: ImageCapture.OutputFileResults) -> Unit = {},
        onErrorCreate:(e:Exception) -> Unit = {}
    ) {
        onCreate()
        val imageCapture = ImageCapture.Builder().build()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
        val cameraProvider = ProcessCameraProvider.getInstance(context).get()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
                this, CameraSelector.DEFAULT_FRONT_CAMERA,imageCapture)

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onSuccess(outputFileResults)
                    onDestroy(cameraProvider)
                }

                override fun onError(exception: ImageCaptureException) {
                    onErrorCreate(exception)
                    onDestroy(cameraProvider)
                }

            }
        )

    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}