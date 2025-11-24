package com.xxmrk888ytxx.workers.Workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.logcatMessageD
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import kotlinx.coroutines.delay
import java.io.File

/**
 * [Ru]
 * Данный Worker предназначен для создания изображения через фронтальную камеру
 * для его работы необходимо передать обязательные параметры(параметры должны быть переданы
 * через класс [androidx.work.Data], ключи для передачи параметров можно найти в companion object)
 * @param [ImagePath] - Место куда необходимо сохранить изображение.
 *
 * При успешном создании изображения, путь к ему будет передан дальше,
 * под ключом [SendPhotoTelegramWorker.photoPathDataKey], для возможности использования изображения
 * в другом worker
 *
 * Если не передать обязательные параметры будет выброшено исключение
 * @throws IllegalArgumentException
 */

/**
 * [En]
 * This Worker is designed to create an image through the front camera
 * for it to work, you must pass required parameters (parameters must be passed
 * via the [androidx.work.Data] class, the keys for passing parameters can be found in the companion object)
 * @param [ImagePath] - The location where the image should be saved.
 *
 * If the image is successfully created, the path to it will be passed on,
 * under the key [SendPhotoTelegramWorker.photoPathDataKey], to be able to use the image
 * in another worker
 *
 * If you do not pass the required parameters, an exception will be thrown
 * @throws IllegalArgumentException
 */
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
        workerComponent.cameraManager.get()
    }

    private val imagePath: File by lazy {
        val path = params.inputData.getString(ImagePath) ?: throw IllegalArgumentException(
            "For this worker need send four params:ImagePath.Key for send data in worker you can" +
                    "found in companion object")
        File(path)
    }

    private fun isHaveCameraPermission(): Boolean {
        return ContextCompat
            .checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }

    override suspend fun doWork(): Result {
        if (!isHaveCameraPermission()) throw Exception("Application haven't permission to camera." +
                "Request ${Manifest.permission.CAMERA} permission")
        try {
            var result:Result? = null

            cameraManager.createPhoto(
                imagePath,
                onErrorCreate = {
                    logcatMessageD("Error create image e:${it.stackTraceToString()}")
                    result = Result.retry()
                },
                onSuccess = {
                    val output = Data
                        .Builder()
                        .putString(SendPhotoTelegramWorker.PHOTO_PATH_DATA_KEY, imagePath.absolutePath)
                        .build()
                    result = Result.success(output)
                }
            )

            while (result == null) { delay(10) }

            return result!!
        }catch (e:IllegalArgumentException) {
            throw e
        }
        catch (e:Exception) {
            logcatMessageD("Error create image in worker e:${e.stackTraceToString()}")
            return Result.retry()
        }
    }

    companion object {
        const val ImagePath = "ImagePath"
    }
}