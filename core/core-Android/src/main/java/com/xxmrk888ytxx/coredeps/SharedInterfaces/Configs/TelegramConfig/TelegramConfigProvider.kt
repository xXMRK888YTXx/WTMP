package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig

import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Данный интерфейс должен предоставлять ранее сохранённый [TelegramConfig]. Если ранее он не был
 * установлен, то должен вернуть null
 */
/**
 * [En]
 * This interface must provide a previously saved [TelegramConfig]. If he was not previously
 * is set, it should return null
 */
interface TelegramConfigProvider {
    val telegramConfig: Flow<TelegramConfig?>
}