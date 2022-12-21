package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.observer.domain.TelegramConfig.TelegramConfigManager
import dagger.Binds
import dagger.Module

@Module
interface TelegramConfigModule {
    @Binds
    fun bindTelegramConfigProvider(telegramConfigManager: TelegramConfigManager) : TelegramConfigProvider

    @Binds
    fun bindTelegramConfigChanger(telegramConfigManager: TelegramConfigManager) : TelegramConfigChanger
}