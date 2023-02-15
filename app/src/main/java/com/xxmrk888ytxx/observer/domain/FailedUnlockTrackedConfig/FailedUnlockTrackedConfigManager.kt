package com.xxmrk888ytxx.observer.domain.FailedUnlockTrackedConfig

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

internal class FailedUnlockTrackedConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager,
) : FailedUnlockTrackedConfigChanger,
    FailedUnlockTrackedConfigProvider {
    private val isTrackedKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/isTrackedKey")
    private val operationTimeLimit =
        intPreferencesKey("FailedUnlockTrackedConfigKeys/operationTimeLimit")
    private val countFailedUnlockToTriggerKey =
        intPreferencesKey("FailedUnlockTrackedConfigKeys/countFailedUnlockToTriggerKey")
    private val makePhotoKey = booleanPreferencesKey("FailedUnlockTrackedConfigKeys/mackPhotoKey")
    private val notifyInTelegram =
        booleanPreferencesKey("FailedUnlockTrackedConfigKeys/notifyInTelegramKey")
    private val joinPhotoToTelegramNotify =
        booleanPreferencesKey("FailedUnlockTrackedConfigKeys/joinPhotoToTelegramNotify")

    //FailedUnlockTrackedConfigChanger
    override suspend fun updateIsTracked(state: Boolean) {
        if (!state) {
            updateMakePhoto(false)
            updateNotifyInTelegram(false)
            updateJoinPhotoToTelegramNotify(false)
        }
        settingsAppManager.writeProperty(
            isTrackedKey,
            state
        )
    }

    override suspend fun updateCountFailedUnlockToTrigger(newCount: Int) {
        settingsAppManager.writeProperty(countFailedUnlockToTriggerKey, newCount)
    }

    override suspend fun updateTimeOperationLimit(newTime: Int) {
        settingsAppManager.writeProperty(operationTimeLimit, newTime)
    }

    override suspend fun updateMakePhoto(state: Boolean) {
        if (!state) updateJoinPhotoToTelegramNotify(false)
        settingsAppManager.writeProperty(
            makePhotoKey,
            state
        )
    }

    override suspend fun updateNotifyInTelegram(state: Boolean) {
        if (!state) updateJoinPhotoToTelegramNotify(false)
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
            val isTrackedFlow = settingsAppManager.getProperty(isTrackedKey, false)
            val operationTimeLimitFlow = settingsAppManager.getProperty(operationTimeLimit, 0)
            val countFailedUnlockToTriggerFlow = settingsAppManager
                .getProperty(countFailedUnlockToTriggerKey, 1)
            val makePhotoFlow = settingsAppManager.getProperty(makePhotoKey, false)
            val notifyInTelegramFlow = settingsAppManager.getProperty(notifyInTelegram, false)
            val joinPhotoToTelegramNotifyFlow = settingsAppManager.getProperty(
                joinPhotoToTelegramNotify,
                false
            )

            return combine(
                isTrackedFlow,
                operationTimeLimitFlow,
                countFailedUnlockToTriggerFlow,
                makePhotoFlow,
                notifyInTelegramFlow,
                joinPhotoToTelegramNotifyFlow
            ) {
                FailedUnlockTrackedConfig(
                    isTracked = it[0] as Boolean,
                    timeOperationLimit = it[1] as Int,
                    countFailedUnlockToTrigger = it[2] as Int,
                    makePhoto = it[3] as Boolean,
                    notifyInTelegram = it[4] as Boolean,
                    joinPhotoToTelegramNotify = it[5] as Boolean
                )
            }
        }
}