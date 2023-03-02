package com.xxmrk888ytxx.camera

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CameraManager
import com.xxmrk888ytxx.coredeps.logcatMessageD
import java.io.File
import javax.inject.Inject
import kotlin.math.log

/**
 * [Ru]
 * Реализация для интерфейса [CameraManager]
 *
 * [En]
 *  Implementation for the [CameraManager] interface
 */
class CameraManagerImpl @Inject constructor(private val context: Context) : LifecycleOwner,
    CameraManager {

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private fun onCreate() {
        handler.post { lifecycleRegistry.currentState = Lifecycle.State.RESUMED }
    }

    private fun onDestroy(cameraProvider: ProcessCameraProvider) {
        handler.post {
            cameraProvider.unbindAll()
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }
    }

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override fun createPhoto(
        outputFile: File,
        onSuccess:() -> Unit,
        onErrorCreate:(e:Exception) -> Unit
    ) {
        try {
            onCreate()
            val imageCapture = ImageCapture
                .Builder()
                .setJpegQuality(80)
                .setTargetResolution(Size(1600,1200))
                .build()
            val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
            handler.post {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, CameraSelector.DEFAULT_FRONT_CAMERA,imageCapture)
            }
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        onSuccess()
                        onDestroy(cameraProvider)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        onErrorCreate(exception)
                        onDestroy(cameraProvider)
                    }

                }
            )
        }catch (e:Exception) {
            if(lifecycle.currentState == Lifecycle.State.RESUMED) {
                try {
                    onDestroy(ProcessCameraProvider.getInstance(context).get())
                }catch (e:Exception) {
                    logcatMessageD("Error unBind camera after exception ${e.printStackTrace()}")
                }
            }
            onErrorCreate(e)
            logcatMessageD("Create photo error ${e.printStackTrace()}")
        }

    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}