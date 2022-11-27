package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.api_telegram.TelegramRepositoryFactoryImpl
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import dagger.Binds
import dagger.Module

@Module
interface TelegramRepositoryFactoryModule {
    @Binds
    fun bindTelegramRepositoryFactory(
        telegramRepositoryFactoryModule: TelegramRepositoryFactoryImpl
    ) : TelegramRepositoryFactory
}