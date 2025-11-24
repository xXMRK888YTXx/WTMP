package com.xxmrk888ytxx.workers.Workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.workers.DI.DaggerWorkerComponent
import com.xxmrk888ytxx.workers.DI.WorkerComponent

/**
 * [Ru]
 * Данный Worker предназначен для отправки изображений в телеграм
 * для его работы необходимо передать обязательные параметры(параметры должны быть переданы
 * через класс [androidx.work.Data], ключи для передачи параметров можно найти в companion object)
 * @param [TELEGRAM_BOT_KEY_DATA_KEY] - Ключ для доступа к боту Telegram
 * @param [USER_ID_DATA_KEY] - Id пользователя которому будет отправлено изображение
 * @param [PHOTO_PATH_DATA_KEY] - Путь нахождения изображение
 * Так же по желанию можно передать следующие параметры
 * @param [CAPTION_DATA_KEY] - Подпись к отправленному изображению, если не указать
 * то фотография будет без подписи.
 * Если не передать обязательные параметры будет выброшено исключение
 * @throws IllegalArgumentException
 * [En]
 * This Worker is designed to send images to Telegram
 * for it to work, you must pass required parameters (parameters must be passed
 * via the [androidx.work.Data] class, the keys for passing parameters can be found in the companion object)
 * @param [TELEGRAM_BOT_KEY_DATA_KEY] - Key to access the Telegram bot
 * @param [USER_ID_DATA_KEY] - Id of the user to whom the image will be sent
 * @param [PHOTO_PATH_DATA_KEY] - Path to find the image
 * You can also pass the following parameters if you wish.
 * @param [CAPTION_DATA_KEY] - Caption for the submitted image, if not specified
 * then the photo will be without a caption.
 * If the required parameters are not passed, an exception will be thrown.
 * @throws IllegalArgumentException
 *
 */
internal class SendPhotoTelegramWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val exception = IllegalArgumentException(
        "For this worker need send four params: botKey,userId," +
                "photoPath,caption" +
                "Key for send data in worker you can" +
                " found in companion object"
    )

    private val component: WorkerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(context.getDepsByApplication())
    }

    private val telegramRepository: TelegramRepository by lazy {
        val botKey = workerParams.inputData.getString(TELEGRAM_BOT_KEY_DATA_KEY)
        val userId = workerParams.inputData.getLong(USER_ID_DATA_KEY, -1)
        if (botKey != null && userId != -1L) {
            component.telegramRepositoryFactory.get().create(botKey, userId)
        } else {
            throw exception
        }
    }

    private val photoPath by lazy {
        workerParams.inputData.getString(PHOTO_PATH_DATA_KEY) ?: throw exception
    }

    internal fun getBitmapFromPath() = BitmapFactory.decodeFile(photoPath)

    override suspend fun doWork(): Result {
        return try {
            val bitmap = getBitmapFromPath()
            telegramRepository.sendPhoto(
                bitmap,
                workerParams.inputData.getString(CAPTION_DATA_KEY) ?: ""
            )
            Result.success()
        } catch (e: IllegalArgumentException) {
            throw e
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val TELEGRAM_BOT_KEY_DATA_KEY = "telegramBotKeyDataKey"
        const val USER_ID_DATA_KEY = "userIdDataKey"
        const val PHOTO_PATH_DATA_KEY = "photoPathDataKey"
        const val CAPTION_DATA_KEY = "captionDataKey"
    }

}