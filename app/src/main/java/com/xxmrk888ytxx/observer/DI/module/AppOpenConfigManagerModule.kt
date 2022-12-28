package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.observer.domain.AppOpenConfig.AppOpenConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface AppOpenConfigManagerModule {
    @Binds
    fun bindAppOpenConfigProvider(appOpenConfigManager: AppOpenConfigManager) : AppOpenConfigProvider

    @Binds
    fun bindAppOpenConfigChanger(appOpenConfigManager: AppOpenConfigManager) : AppOpenConfigChanger
}