package com.xxmrk888ytxx.api_telegram.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep data class TelegramRequestResult constructor(
   @SerializedName("ok") val isSuccessful:Boolean
) {
   @Keep
   override fun toString(): String {
      return super.toString()
   }
}