package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.observer.domain.StorageConfigManager.StorageConfigManager
import dagger.Binds
import dagger.Module

@Module
internal interface StorageConfigManagerModule {
    @Binds
    fun bindStorageConfigChanger(storageConfigManager: StorageConfigManager) : StorageConfigChanger

    @Binds
    fun bindStorageConfigProvider(storageConfigManager: StorageConfigManager) : StorageConfigProvider
}