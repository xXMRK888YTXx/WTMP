package com.xxmrk888ytxx.coredeps.Repository

import android.graphics.Bitmap

interface TelegramRepository {

    suspend fun sendMessage(text:String)

    suspend fun sendPhoto(image: Bitmap,caption:String = "")
}