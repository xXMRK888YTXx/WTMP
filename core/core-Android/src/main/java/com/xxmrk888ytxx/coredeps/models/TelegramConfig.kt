package com.xxmrk888ytxx.coredeps.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TelegramConfig(
    @Json(name = "userId") val userId:Long,
    @Json(name = "botKey") val botKey:String
)
