package com.xxmrk888ytxx.workers.Workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import com.xxmrk888ytxx.workers.DI.WorkerComponent

/**
 * [Ru]
 * Данный Worker предназначен для отправки сообщений в телеграм
 * для его работы необходимо передать обязательные параметры(параметры должны быть переданы
 * через класс [androidx.work.Data], ключи для передачи параметров можно найти в companion object)
 * @param [TELEGRAM_BOT_KEY_DATA_KEY] - Ключ для доступа к боту Telegram
 * @param [USER_ID_DATA_KEY] - Id пользователя которому будет отправлено изображение
 * @param [TEXT_DATA_KEY] - Текст сообщения
 * Если не передать обязательные параметры будет выброшено исключение
 * @throws IllegalArgumentException
 * [En]
 * This Worker is designed to send messages to Telegram
 * for it to work, you must pass required parameters (parameters must be passed
 * via the [androidx.work.Data] class, the keys for passing parameters can be found in the companion object)
 * @param [TELEGRAM_BOT_KEY_DATA_KEY] - Key to access the Telegram bot
 * @param [USER_ID_DATA_KEY] - Id of the user to whom the image will be sent
 * @param [TEXT_DATA_KEY] - Message text
 * If you do not pass the required parameters, an exception will be thrown
 * @throws IllegalArgumentException
 */
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
        val botKey = workerParams.inputData.getString(TELEGRAM_BOT_KEY_DATA_KEY)
        val userId = workerParams.inputData.getLong(USER_ID_DATA_KEY,-1)
        if(botKey != null&&userId != -1L) {
            component.telegramRepositoryFactory.get().create(botKey, userId)
        }
        else {
            throw IllegalArgumentException("For this worker need send two params: botKey,userId" +
                    "You send: botkey = $botKey,userId = $userId. Key for send data in worker you can" +
                    " found in companion object")
        }
    }



    override suspend fun doWork(): Result {
        return try {
            telegramRepository.sendMessage(workerParams.inputData.getString(TEXT_DATA_KEY) ?: " ")
            Result.success()
        }catch (e:IllegalArgumentException) {
            throw e
        }
        catch (e:Exception) {
            Result.retry()
        }
    }

    companion object {
        const val TELEGRAM_BOT_KEY_DATA_KEY = "telegramBotKeyDataKey"
        const val USER_ID_DATA_KEY = "userIdDataKey"
        const val TEXT_DATA_KEY = "textDataKey"
    }

}