package com.xxmrk888ytxx.observer.domain.StorageConfigManager

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.models.StorageConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class StorageConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager,
) : StorageConfigChanger, StorageConfigProvider {

    private val maxReportCountKey = intPreferencesKey("StorageConfig/maxReportCountKey")
    private val maxReportStorageTime = longPreferencesKey("StorageConfig/maxReportStorageTime")

    override suspend fun updateMaxReportCount(newValue: Int) {
        settingsAppManager.writeProperty(maxReportCountKey, newValue)
    }

    override suspend fun updateMaxReportStorageTime(maxValue: Long) {
        settingsAppManager.writeProperty(maxReportStorageTime, maxValue)
    }

    override val storageConfig: Flow<StorageConfig>
        get() {
            val maxReportCountFlow = settingsAppManager.getProperty(maxReportCountKey, 0)
            val maxReportStorageTime = settingsAppManager.getProperty(maxReportStorageTime, 0)

            return combine(
                maxReportCountFlow,
                maxReportStorageTime
            ) { maxReportCount: Int, maxReportStorage: Long ->
                StorageConfig(maxReportCount, maxReportStorage)
            }
        }

}