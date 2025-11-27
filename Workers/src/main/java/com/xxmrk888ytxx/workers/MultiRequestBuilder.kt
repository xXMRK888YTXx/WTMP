package com.xxmrk888ytxx.workers

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import com.xxmrk888ytxx.coredeps.SharedInterfaces.SingleWorkWorkerManager
import com.xxmrk888ytxx.workers.Workers.MakeImageWorker
import com.xxmrk888ytxx.workers.Workers.SendPhotoTelegramWorker
import com.xxmrk888ytxx.workers.Workers.SendTelegramMessageWorker

internal class MultiRequestBuilder : SingleWorkWorkerManager {

    internal var sendMessageTelegramWorker: OneTimeWorkRequest? = null
    private set

    internal var sendPhotoTelegramWorker:OneTimeWorkRequest? = null
    private set

    internal var createImageWorker:OneTimeWorkRequest? = null
    private set

    override fun sendMessageTelegram(botKey: String, userId: Long, message: String) {
        val data = Data.Builder()
            .putString(SendTelegramMessageWorker.TEXT_DATA_KEY,message)
            .putString(SendTelegramMessageWorker.TELEGRAM_BOT_KEY_DATA_KEY,botKey)
            .putLong(SendTelegramMessageWorker.USER_ID_DATA_KEY,userId)
            .build()

        val worker = OneTimeWorkRequestBuilder<SendTelegramMessageWorker>()
            .setInputData(data)
            .build()

        sendMessageTelegramWorker = worker
    }

    override fun sendPhotoTelegram(
        botKey: String,
        userId: Long,
        photoPath: String?,
        caption: String,
    ) {
        val data = Data.Builder()
            .putString(SendPhotoTelegramWorker.TELEGRAM_BOT_KEY_DATA_KEY,botKey)
            .putLong(SendPhotoTelegramWorker.USER_ID_DATA_KEY,userId)
            .putString(SendPhotoTelegramWorker.PHOTO_PATH_DATA_KEY,photoPath)
            .putString(SendPhotoTelegramWorker.CAPTION_DATA_KEY,caption)
            .build()

        val worker = OneTimeWorkRequestBuilder<SendPhotoTelegramWorker>()
            .setInputData(data)
            .build()
        sendPhotoTelegramWorker = worker
    }

    override fun createImageWorker(imageDir: String) {
        val data = Data.Builder().putString(MakeImageWorker.ImagePath,imageDir).build()

        val worker = OneTimeWorkRequestBuilder<MakeImageWorker>()
            .setInputData(data)
            .build()

        createImageWorker = worker
    }

    override suspend fun createMultiRequest(request: suspend SingleWorkWorkerManager.() -> Unit) {}

    internal val isValidRequest:Boolean
        get() = sendMessageTelegramWorker != null||
                sendPhotoTelegramWorker != null||
                createImageWorker != null


}