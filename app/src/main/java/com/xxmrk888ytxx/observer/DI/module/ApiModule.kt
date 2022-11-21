package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.api_telegram.TelegramApi
import com.xxmrk888ytxx.api_telegram.telegramApiFactory
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    fun provideApiTelegram() : TelegramApi = telegramApiFactory()
}