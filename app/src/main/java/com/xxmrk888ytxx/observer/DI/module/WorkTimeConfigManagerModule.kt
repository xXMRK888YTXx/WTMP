package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.observer.domain.WorkTimeConfig.WorkTimeConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface WorkTimeConfigManagerModule {
    @Binds
    fun bindWorkConfigProvider(
        workTimeConfigManager: WorkTimeConfigManager
    ) : WorkTimeConfigProvider

    @Binds
    fun bindWorkConfigChanger(
        workTimeConfigManager: WorkTimeConfigManager
    ) : WorkTimeConfigChanger
}