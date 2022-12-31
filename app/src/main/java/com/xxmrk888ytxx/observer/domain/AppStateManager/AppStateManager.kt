package com.xxmrk888ytxx.observer.domain.AppStateManager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AppStateManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : AppStateChanger, AppStateProvider {

    private val appStateKey = booleanPreferencesKey("appStateKey")

    override suspend fun updateAppState(state: Boolean) {
        settingsAppManager.writeProperty(appStateKey,state)
    }

    override val isAppEnable: Flow<Boolean>
        get() {
            return settingsAppManager.getProperty(appStateKey,false)
        }
}