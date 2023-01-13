package com.xxmrk888ytxx.observer.domain.AdStateManager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.adutils.AdStateManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AdStateManagerImpl @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : AdStateManager {

    private val adStateKey = booleanPreferencesKey("adStateKey")

    override suspend fun changeAdState(state: Boolean) {
        settingsAppManager.writeProperty(adStateKey,state)
    }

    override val isNeedShowAd: Flow<Boolean>
        get() = settingsAppManager.getProperty(adStateKey,true)
}