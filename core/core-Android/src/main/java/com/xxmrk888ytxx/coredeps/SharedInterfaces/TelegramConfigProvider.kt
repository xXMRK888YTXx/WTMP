package com.xxmrk888ytxx.coredeps.SharedInterfaces

import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import kotlinx.coroutines.flow.Flow

interface TelegramConfigProvider {
    fun getTelegramConfig() : Flow<TelegramConfig?>
}