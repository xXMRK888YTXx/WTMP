package com.xxmrk888ytxx.coredeps.SharedInterfaces

import com.xxmrk888ytxx.coredeps.models.TelegramConfig

interface TelegramConfigChanger {
    suspend fun updateTelegramConfig(telegramConfig: TelegramConfig)
}