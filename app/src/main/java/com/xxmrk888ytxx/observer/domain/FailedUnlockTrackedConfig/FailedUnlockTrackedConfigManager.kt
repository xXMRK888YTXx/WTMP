package com.xxmrk888ytxx.observer.domain.FailedUnlockTrackedConfig

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class FailedUnlockTrackedConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : FailedUnlockTrackedConfigChanger,
    FailedUnlockTrackedConfigProvider {
    private val isTrackedKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/isTrackedKey")
    private val makePhotoKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/mackPhotoKey")
    private val notifyInTelegram = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/notifyInTelegramKey")
    private val joinPhotoToTelegramNotify = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/joinPhotoToTelegramNotify")
    //FailedUnlockTrackedConfigChanger
    override suspend fun updateIsTracked(state: Boolean) {
        if(!state) {
            updateMakePhoto(false)
            updateNotifyInTelegram(false)
            updateJoinPhotoToTelegramNotify(false)
        }
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

    //FailedUnlockTrackedConfigProvider
    override val config: Flow<FailedUnlockTrackedConfig>
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
                FailedUnlockTrackedConfig(
                    isTracked, makePhoto, notifyInTelegram, joinPhotoToTelegramNotify
                )
            }
        }
}