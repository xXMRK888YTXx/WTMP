package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig

/**
 * [Ru]
 * * Интерфейс для управление настройками которые представлены [StorageConfig]
 */
/**
 * [En]
 * * Interface for managing the settings that are presented by [StorageConfig]
 */
interface StorageConfigChanger {
    suspend fun updateMaxReportCount(newValue:Int)

    suspend fun updateMaxReportStorageTime(maxValue: Long)
}