package com.xxmrk888ytxx.observer.domain.UseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class HandleEventUseCaseImpl @Inject constructor(
    private val workerManager: WorkerManager,
    private val telegramConfigProvider: TelegramConfigProvider,
    private val imageRepository: ImageRepository
) : HandleEventUseCase {

    override suspend fun execute(
        eventId:Int,
        isSendTelegramMessage:Boolean,
        makePhoto:Boolean,
        joinPhotoToTelegramNotify:Boolean,
        telegramMessageText:String
    ) {
        workerManager.createMultiRequest {
            if(isSendTelegramMessage&&!joinPhotoToTelegramNotify) {
                val telegramConfig = telegramConfigProvider.telegramConfig.first()
                if(telegramConfig != null) {
                    sendMessageTelegram(
                        botKey = telegramConfig.botKey,
                        userId = telegramConfig.userId,
                        telegramMessageText
                    )
                }
            }

            if(makePhoto) {
                createImageWorker(imageRepository.getSaveImageFile(eventId).absolutePath)
            }

            if(makePhoto&&joinPhotoToTelegramNotify) {
                val telegramConfig = telegramConfigProvider.telegramConfig.first()
                if(telegramConfig != null) {
                    sendPhotoTelegram(
                        botKey = telegramConfig.botKey,
                        userId = telegramConfig.userId,
                        null,
                        caption = telegramMessageText
                    )
                }
            }
        }
    }
}