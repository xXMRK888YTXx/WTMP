package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.LocalizationManager
import com.xxmrk888ytxx.observer.domain.LocalizationManager.LocalizationManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface LocalizationManagerModule {
    @Binds
    fun bindLocalizationManager(
        localizationManagerImpl: LocalizationManagerImpl
    ) : LocalizationManager
}