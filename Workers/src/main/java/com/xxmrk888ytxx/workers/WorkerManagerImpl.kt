package com.xxmrk888ytxx.workers

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import com.xxmrk888ytxx.workers.Workers.MakeImageWorker
import com.xxmrk888ytxx.workers.Workers.SendPhotoTelegramWorker
import com.xxmrk888ytxx.workers.Workers.SendTelegramMessageWorker
import javax.inject.Inject

class WorkerManagerImpl @Inject constructor(
    private val context:Context
) : WorkerManager {
    override fun sendMessageTelegram(botKey: String, userId: Long, message: String) {
        val data = Data.Builder()
            .putString(SendTelegramMessageWorker.textDataKey,message)
            .putString(SendTelegramMessageWorker.telegramBotKeyDataKey,botKey)
            .putLong(SendTelegramMessageWorker.userIdDataKey,userId)
            .build()

        val worker = OneTimeWorkRequestBuilder<SendTelegramMessageWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork("SendTelegramMessageWorker", ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
    }

    override fun sendPhotoTelegram(
        botKey: String,
        userId: Long,
        photoPath: String?,
        caption: String,
    ) {
        val data = Data.Builder()
            .putString(SendPhotoTelegramWorker.telegramBotKeyDataKey,botKey)
            .putLong(SendPhotoTelegramWorker.userIdDataKey,userId)
            .putString(SendPhotoTelegramWorker.photoPathDataKey,photoPath)
            .putString(SendPhotoTelegramWorker.captionDataKey,caption)
            .build()

        val worker = OneTimeWorkRequestBuilder<SendPhotoTelegramWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork("SendPhotoTelegramWorker",
            ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
    }

    override fun createImageWorker(imageDir:String) {
        val data = Data.Builder().putString(MakeImageWorker.ImagePath,imageDir).build()

        val worker = OneTimeWorkRequestBuilder<MakeImageWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork("MakeImageWorker",
            ExistingWorkPolicy.APPEND_OR_REPLACE,worker)
    }

    override suspend fun createMultiRequest(request:suspend WorkerManager.() -> Unit) {
        val builder = MultiRequestBuilder()
        request(builder)
        if(!builder.isValidRequest) return
        val sendMessageTelegramWorker = builder.sendMessageTelegramWorker
        val sendPhotoTelegramWorker = builder.sendPhotoTelegramWorker
        val createImageWorker = builder.createImageWorker

        if(sendMessageTelegramWorker != null) {
            WorkManager.getInstance(context)
            WorkManager.getInstance(context).enqueueUniqueWork("SendPhotoTelegramWorker",
                ExistingWorkPolicy.APPEND_OR_REPLACE,sendMessageTelegramWorker)
        }

        if(sendPhotoTelegramWorker != null&&createImageWorker != null) {
            WorkManager.getInstance(context)
                .beginWith(createImageWorker)
                .then(sendPhotoTelegramWorker)
                .enqueue()
        } else {
            if(sendMessageTelegramWorker != null) {
                WorkManager.getInstance(context).enqueueUniqueWork("SendPhotoTelegramWorker",
                    ExistingWorkPolicy.APPEND_OR_REPLACE,sendMessageTelegramWorker)
            }

            if(createImageWorker != null) {
                WorkManager.getInstance(context).enqueueUniqueWork("MakeImageWorker",
                    ExistingWorkPolicy.APPEND_OR_REPLACE,createImageWorker)
            }
        }


    }
}