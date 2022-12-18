package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigProvider
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import dagger.Binds
import dagger.Module

@Module
interface TelegramConfigModule {
    @Binds
    fun bindTelegramConfigProvider(settingsAppManager: SettingsAppManager) : TelegramConfigProvider

    @Binds
    fun bindTelegramConfigChanger(settingsAppManager: SettingsAppManager) : TelegramConfigChanger
}