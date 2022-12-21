package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.observer.domain.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface FailedUnlockTrackedConfigModule {
    @Binds
    fun bindFailedUnlockTrackedConfigProvider(
        failedUnlockTrackedConfigManager: FailedUnlockTrackedConfigManager
    ) : FailedUnlockTrackedConfigProvider

    @Binds
    fun bindFailedUnlockTrackedConfigChanger(
        failedUnlockTrackedConfigManager: FailedUnlockTrackedConfigManager
    ) : FailedUnlockTrackedConfigChanger
}