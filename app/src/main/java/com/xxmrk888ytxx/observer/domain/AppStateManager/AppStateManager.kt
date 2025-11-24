package com.xxmrk888ytxx.observer.domain.AppStateManager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PeriodicWorkWorkerManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AppStateManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager,
    private val periodicWorkWorkerManager: PeriodicWorkWorkerManager
) : AppStateChanger, AppStateProvider {

    private val appStateKey = booleanPreferencesKey(APP_STATE_KEY)

    override suspend fun updateAppState(state: Boolean) {
        settingsAppManager.writeProperty(appStateKey, state)

        when(state) {
            true -> periodicWorkWorkerManager.enableServiceWorker()
            false -> periodicWorkWorkerManager.disableServiceWorker()
        }
    }

    override val isAppEnable: Flow<Boolean>
        get() = settingsAppManager.getProperty(appStateKey, false)

    private companion object {
        const val APP_STATE_KEY = "appStateKey"
    }
}