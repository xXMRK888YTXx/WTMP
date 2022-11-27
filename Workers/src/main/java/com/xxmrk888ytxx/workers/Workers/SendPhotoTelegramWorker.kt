package com.xxmrk888ytxx.workers.Workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import com.xxmrk888ytxx.workers.DI.WorkerComponent

internal class SendPhotoTelegramWorker(
    private val context: Context,
    private val workerParams:WorkerParameters
) : CoroutineWorker(context,workerParams)  {

    private val exception =  IllegalArgumentException("For this worker need send four params: botKey,userId," +
            "photoPath,caption" +
            "Key for send data in worker you can" +
            " found in companion object")

    private val component : WorkerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(context.getDepsByApplication())
    }

    private val telegramRepository: TelegramRepository by lazy {
        val botKey = workerParams.inputData.getString(telegramBotKeyDataKey)
        val userId = workerParams.inputData.getLong(userIdDataKey,-1)
        if(botKey != null&&userId != -1L) {
            component.telegramRepositoryFactory.create(botKey, userId)
        }
        else {
            throw exception
        }
    }

    private val photoPath by lazy {
        workerParams.inputData.getString(photoPathDataKey) ?: throw exception
    }

    override suspend fun doWork(): Result {
        telegramRepository.sendPhoto(BitmapFactory.decodeFile(photoPath),
            workerParams.inputData.getString(captionDataKey) ?: "")
        return Result.success()
    }

    companion object {
        const val telegramBotKeyDataKey = "telegramBotKeyDataKey"
        const val userIdDataKey = "userIdDataKey"
        const val photoPathDataKey = "photoPathDataKey"
        const val captionDataKey = "captionDataKey"
    }

}