package com.xxmrk888ytxx.coredeps.Repository

import com.xxmrk888ytxx.coredeps.ApplicationScope
import kotlinx.coroutines.CoroutineScope

interface TelegramRepository {
    fun sendMessage(
        text:String,
        coroutinesScope: CoroutineScope = ApplicationScope,
        onSuccessful:() -> Unit = {},
        onError:(e:Exception,body:String?) -> Unit = {e,body ->},
    )
}