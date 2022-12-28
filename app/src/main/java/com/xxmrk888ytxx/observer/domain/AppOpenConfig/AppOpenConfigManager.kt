package com.xxmrk888ytxx.observer.domain.AppOpenConfig

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class AppOpenConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : AppOpenConfigChanger,AppOpenConfigProvider {
    private val isTrackedKey = booleanPreferencesKey("AppOpenConfigManager/isTrackedKey")
    private val makePhotoKey = booleanPreferencesKey("AppOpenConfigManager/mackPhotoKey")
    private val notifyInTelegram = booleanPreferencesKey("AppOpenConfigManager/notifyInTelegramKey")
    private val joinPhotoToTelegramNotify =
        booleanPreferencesKey("AppOpenConfigManager/joinPhotoToTelegramNotify")

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
        if(!state) updateJoinPhotoToTelegramNotify(false)

        settingsAppManager.writeProperty(
            makePhotoKey,
            state
        )
    }

    override suspend fun updateNotifyInTelegram(state: Boolean) {
        if(!state) updateJoinPhotoToTelegramNotify(false)

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

    override val config: Flow<AppOpenConfig>
        get() {
            val isTrackedFlow = settingsAppManager.getProperty(isTrackedKey,false)
            val makePhotoFlow = settingsAppManager.getProperty(makePhotoKey,false)
            val notifyInTelegramFlow = settingsAppManager.getProperty(notifyInTelegram,false)
            val joinPhotoToTelegramNotifyFlow = settingsAppManager.getProperty(
                joinPhotoToTelegramNotify,
                false
            )

            return  combine(isTrackedFlow,makePhotoFlow,
                notifyInTelegramFlow,joinPhotoToTelegramNotifyFlow
            ) { isTracked:Boolean ,makePhoto:Boolean,
               notifyInTelegram:Boolean,joinPhotoToTelegramNotify:Boolean ->
                    AppOpenConfig(isTracked, makePhoto, notifyInTelegram, joinPhotoToTelegramNotify)
            }
        }

}