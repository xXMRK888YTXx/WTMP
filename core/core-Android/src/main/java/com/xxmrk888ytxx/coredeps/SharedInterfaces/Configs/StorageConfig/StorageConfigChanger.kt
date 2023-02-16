package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig

interface StorageConfigChanger {
    suspend fun updateMaxReportCount(newValue:Int)

    suspend fun updateMaxReportStorageTime(maxValue: Long)
}