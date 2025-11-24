package com.xxmrk888ytxx.workers

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.SingleWorkWorkerManager
import com.xxmrk888ytxx.workers.Workers.MakeImageWorker
import com.xxmrk888ytxx.workers.Workers.SendPhotoTelegramWorker
import com.xxmrk888ytxx.workers.Workers.SendTelegramMessageWorker
import javax.inject.Inject

class SingleWorkWorkerManagerImpl @Inject constructor(
    private val context:Context
) : SingleWorkWorkerManager {
    override fun sendMessageTelegram(botKey: String, userId: Long, message: String) {
        val data = Data.Builder()
            .putString(SendTelegramMessageWorker.TEXT_DATA_KEY,message)
            .putString(SendTelegramMessageWorker.TELEGRAM_BOT_KEY_DATA_KEY,botKey)
            .putLong(SendTelegramMessageWorker.USER_ID_DATA_KEY,userId)
            .build()

        val worker = OneTimeWorkRequestBuilder<SendTelegramMessageWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(SEND_TELEGRAM_MESSAGE_WORKER_TAG, ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
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

        WorkManager.getInstance(context).enqueueUniqueWork(SEND_PHOTO_TELEGRAM_WORKER_TAG,
            ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
    }

    override fun createImageWorker(imageDir:String) {
        val data = Data.Builder().putString(MakeImageWorker.ImagePath,imageDir).build()

        val worker = OneTimeWorkRequestBuilder<MakeImageWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(MAKE_IMAGE_WORKER_TAG,
            ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
    }

    override suspend fun createMultiRequest(request:suspend SingleWorkWorkerManager.() -> Unit) {
        val builder = MultiRequestBuilder()
        request(builder)
        if(!builder.isValidRequest) return
        val sendMessageTelegramWorker = builder.sendMessageTelegramWorker
        val sendPhotoTelegramWorker = builder.sendPhotoTelegramWorker
        val createImageWorker = builder.createImageWorker

        if(sendMessageTelegramWorker != null) {
            WorkManager.getInstance(context)
            WorkManager.getInstance(context).enqueueUniqueWork(SEND_TELEGRAM_MESSAGE_WORKER_TAG,
                ExistingWorkPolicy.APPEND_OR_REPLACE,sendMessageTelegramWorker)
        }

        if(sendPhotoTelegramWorker != null&&createImageWorker != null) {
            WorkManager.getInstance(context)
                .beginWith(createImageWorker)
                .then(sendPhotoTelegramWorker)
                .enqueue()
        } else {
            if(sendMessageTelegramWorker != null) {
                WorkManager.getInstance(context).enqueueUniqueWork(SEND_TELEGRAM_MESSAGE_WORKER_TAG,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,sendMessageTelegramWorker)
            }

            if(createImageWorker != null) {
                WorkManager.getInstance(context).enqueueUniqueWork(MAKE_IMAGE_WORKER_TAG,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,createImageWorker)
            }
        }


    }

    companion object {
        private const val SEND_TELEGRAM_MESSAGE_WORKER_TAG = "SendTelegramMessageWorker"
        private const val SEND_PHOTO_TELEGRAM_WORKER_TAG = "SendPhotoTelegramWorker"
        private const val MAKE_IMAGE_WORKER_TAG = "MakeImageWorker"
    }
}