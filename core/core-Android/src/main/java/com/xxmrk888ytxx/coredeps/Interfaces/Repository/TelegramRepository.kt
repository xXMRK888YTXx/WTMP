package com.xxmrk888ytxx.coredeps.Interfaces.Repository

import android.graphics.Bitmap

/**
 * [Ru]
 * Через данный интерфейс происходит отправка сообщений, в телеграмм,
 * [En]
 * Through this interface, messages are sent, in telegrams
 */
interface TelegramRepository {

    suspend fun sendMessage(text:String)

    suspend fun sendPhoto(image: Bitmap,caption:String = "")
}