package com.xxmrk888ytxx.api_telegram.models

import com.google.gson.annotations.SerializedName

data class TelegramRequestResult constructor(
   @SerializedName("ok") val isSuccessful:Boolean
)