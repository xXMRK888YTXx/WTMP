package com.xxmrk888ytxx.observer.domain.SucceededUnlockTrackedConfig

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class SucceededUnlockTrackedConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : SucceededUnlockTrackedConfigChanger,SucceededUnlockTrackedConfigProvider {
    private val isTrackedKey = booleanPreferencesKey("SucceededUnlockTrackedConfigKeys/isTrackedKey")
    private val makePhotoKey = booleanPreferencesKey("SucceededUnlockTrackedConfigKeys/mackPhotoKey")
    private val notifyInTelegram = booleanPreferencesKey("SucceededUnlockTrackedConfigKeys/notifyInTelegramKey")
    private val joinPhotoToTelegramNotify = booleanPreferencesKey("SucceededUnlockTrackedConfigKeys/joinPhotoToTelegramNotify")

    override suspend fun updateIsTracked(state: Boolean) {
        settingsAppManager.writeProperty(
            isTrackedKey,
            state
        )
    }

    override suspend fun updateMakePhoto(state: Boolean) {
        settingsAppManager.writeProperty(
            makePhotoKey,
            state
        )
    }

    override suspend fun updateNotifyInTelegram(state: Boolean) {
        settingsAppManager.writeProperty(
            notifyInTelegram,
            state
        )
    }

    override suspend fun updateJoinPhotoToTelegramNotify(state: Boolean) {
        settingsAppManager.writeProperty(
            joinPhotoToTelegramNotify,
            state
        )
    }

    override val config: Flow<SucceededUnlockTrackedConfig>
        get() {
            val isTrackedFlow = settingsAppManager.getProperty(isTrackedKey,false)
            val makePhotoFlow = settingsAppManager.getProperty(makePhotoKey,false)
            val notifyInTelegramFlow = settingsAppManager.getProperty(notifyInTelegram,false)
            val joinPhotoToTelegramNotifyFlow = settingsAppManager.getProperty(
                joinPhotoToTelegramNotify,
                false
            )

            return combine(isTrackedFlow,makePhotoFlow,
                notifyInTelegramFlow,joinPhotoToTelegramNotifyFlow
            ) {
                    isTracked:Boolean ,makePhoto:Boolean,
                    notifyInTelegram:Boolean,joinPhotoToTelegramNotify:Boolean ->
                SucceededUnlockTrackedConfig(
                    isTracked, makePhoto, notifyInTelegram, joinPhotoToTelegramNotify
                )
            }
        }

}