package com.xxmrk888ytxx.observer.domain.DialogShowStateManager

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.DialogShowStateManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DialogShowStateManagerImpl @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : DialogShowStateManager {

    private val ignoreIgnoreBatteryOptimisationDialogShowStateKey =
        booleanPreferencesKey("ignoreIgnoreBatteryOptimisationDialogShowStateKey")

    override suspend fun setupIgnoreIgnoreBatteryOptimisationDialogShowState(state: Boolean) {
        settingsAppManager.writeProperty(ignoreIgnoreBatteryOptimisationDialogShowStateKey,state)
    }

    override val isIgnoreIgnoreBatteryOptimisationDialogNeedShow: Flow<Boolean>
        get() = settingsAppManager
            .getProperty(ignoreIgnoreBatteryOptimisationDialogShowStateKey,true)
}