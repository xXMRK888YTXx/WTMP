package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigProvider
import com.xxmrk888ytxx.observer.domain.BootDeviceTrackedConfig.BootDeviceTrackedConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface BootDeviceTrackedConfigManagerModule {
    @Binds
    fun bindBootDeviceTrackedConfigProvider(
        bootDeviceTrackedConfigManager: BootDeviceTrackedConfigManager
    ) : BootDeviceTrackedConfigProvider

    @Binds
    fun bindBootDeviceTrackedConfigChanger(
        bootDeviceTrackedConfigManager: BootDeviceTrackedConfigManager
    ) : BootDeviceTrackedConfigChanger
}