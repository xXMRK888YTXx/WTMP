package com.xxmrk888ytxx.observer.domain.UseCase

internal interface HandleEventUseCase {
    suspend fun execute(
        eventId:Int,
        isSendTelegramMessage:Boolean,
        makePhoto:Boolean,
        joinPhotoToTelegramNotify:Boolean,
        telegramMessageText:String = ""
    )
}