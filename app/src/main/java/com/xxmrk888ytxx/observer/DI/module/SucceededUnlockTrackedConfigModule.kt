package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.observer.domain.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface SucceededUnlockTrackedConfigModule {
    @Binds
    fun bindSucceededUnlockTrackedConfigProvider(
        succeededUnlockTrackedConfigManager:SucceededUnlockTrackedConfigManager
    ) : SucceededUnlockTrackedConfigProvider

    @Binds
    fun bindSucceededUnlockTrackedConfigChanger(
        succeededUnlockTrackedConfigManager:SucceededUnlockTrackedConfigManager
    ) : SucceededUnlockTrackedConfigChanger
}