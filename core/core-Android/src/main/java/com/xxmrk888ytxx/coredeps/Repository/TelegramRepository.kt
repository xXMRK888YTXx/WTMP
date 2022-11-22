package com.xxmrk888ytxx.coredeps.Repository

import android.graphics.Bitmap
import com.xxmrk888ytxx.coredeps.ApplicationScope
import kotlinx.coroutines.CoroutineScope

interface TelegramRepository {
    fun sendMessage(
        text:String,
        scope: CoroutineScope = ApplicationScope,
        onSuccessful:() -> Unit = {},
        onError:(e:Exception) -> Unit = {},
    )

    fun sendPhoto(
        image: Bitmap,
        caption:String = "",
        scope:CoroutineScope = ApplicationScope,
        onSuccessful:() -> Unit = {},
        onError:(e:Exception) -> Unit = {},
    )
}