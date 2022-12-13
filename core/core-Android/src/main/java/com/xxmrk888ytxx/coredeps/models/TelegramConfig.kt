package com.xxmrk888ytxx.coredeps.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * [Ru]
 * Модель хранит в себе данные от бота в Telegram
 */
/**
 * [En]
 * The model stores data from the bot in Telegram
 */
@JsonClass(generateAdapter = true)
data class TelegramConfig(
    @Json(name = "userId") val userId:Long,
    @Json(name = "botKey") val botKey:String
)
