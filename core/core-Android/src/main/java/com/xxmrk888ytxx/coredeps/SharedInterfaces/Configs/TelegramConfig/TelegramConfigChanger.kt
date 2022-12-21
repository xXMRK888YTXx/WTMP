package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig

import com.xxmrk888ytxx.coredeps.models.TelegramConfig

/**
 * [Ru]
 * Данный интерфейс необходим для обновления хранимого [TelegramConfig]
 */
/**
 * [En]
 * This interface is required to update the stored [TelegramConfig]
 */
interface TelegramConfigChanger {
    suspend fun updateTelegramConfig(telegramConfig: TelegramConfig)
}