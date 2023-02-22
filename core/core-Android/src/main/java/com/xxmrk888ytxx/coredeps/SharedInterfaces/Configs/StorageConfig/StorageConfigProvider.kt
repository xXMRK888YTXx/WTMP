package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig

import com.xxmrk888ytxx.coredeps.models.StorageConfig
import kotlinx.coroutines.flow.Flow

interface StorageConfigProvider {
    val storageConfig: Flow<StorageConfig>
}