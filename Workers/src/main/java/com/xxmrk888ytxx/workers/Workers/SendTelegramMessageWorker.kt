package com.xxmrk888ytxx.workers.Workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import com.xxmrk888ytxx.workers.DI.WorkerComponent

internal class SendTelegramMessageWorker(
    private val context:Context,
    private val workerParams:WorkerParameters
) : CoroutineWorker(context,workerParams) {

    private val component : WorkerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(context.getDepsByApplication())
    }

    private val telegramRepository:TelegramRepository by lazy {
        val botKey = workerParams.inputData.getString(telegramBotKeyDataKey)
        val userId = workerParams.inputData.getLong(userIdDataKey,-1)
        if(botKey != null&&userId != -1L) {
            component.telegramRepositoryFactory.create(botKey, userId)
        }
        else {
            throw IllegalArgumentException("For this worker need send two params: botKey,userId" +
                    "You send: botkey = $botKey,userId = $userId. Key for send data in worker you can" +
                    " found in companion object")
        }
    }



    override suspend fun doWork(): Result {
        telegramRepository.sendMessage(workerParams.inputData.getString(textDataKey) ?: " ")
        return Result.success()
    }

    companion object {
        const val telegramBotKeyDataKey = "telegramBotKeyDataKey"
        const val userIdDataKey = "userIdDataKey"
        const val textDataKey = "textDataKey"
    }

}