package com.xxmrk888ytxx.observer.domain.TelegramConfig

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.fromJson
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.coredeps.toJson
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TelegramConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : TelegramConfigChanger, TelegramConfigProvider {
    private val telegramConfigKey = stringPreferencesKey("telegramConfigKey")

    override suspend fun updateTelegramConfig(telegramConfig: TelegramConfig) {
        settingsAppManager.writeProtectedProperty(
            key = telegramConfigKey,
            value = toJson(telegramConfig)
        )
    }

    override val telegramConfig: Flow<TelegramConfig?>
        get() {
            val jsonString = settingsAppManager.getProtectedProperty(telegramConfigKey)

            return jsonString.map { fromJson(jsonString.first(), TelegramConfig::class.java) }

        }
}