package com.xxmrk888ytxx.observer.domain.WorkTimeConfig

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.WeekDay
import com.xxmrk888ytxx.coredeps.models.WorkTimeConfig
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WorkTimeConfigManager @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : WorkTimeConfigChanger, WorkTimeConfigProvider {

    private val isLimitTimeEnabledKey =
        booleanPreferencesKey("WorkTimeConfigManager/isLimitTimeEnabledKey")
    private val startWorkKey = longPreferencesKey("WorkTimeConfigManager/startWorkKey")
    private val endWorkKey = longPreferencesKey("WorkTimeConfigManager/endWorkKey")
    private val workWeekDaysKey = stringSetPreferencesKey("WorkTimeConfigManager/workWeekDaysKey")


    private fun getIsLimitTimeEnabled(): Flow<Boolean> {
        return settingsAppManager.getProperty(isLimitTimeEnabledKey, false)
    }

    private fun getWorkTimeSpan(): Flow<TimeSpan> {
        val startWorkTime = settingsAppManager.getProperty(startWorkKey, -1)
        val endWorkKey = settingsAppManager.getProperty(endWorkKey, -1)

        return combine(startWorkTime, endWorkKey) { start, end ->
            TimeSpan(start, end)
        }
    }

    private fun getWorkWeekDays(): Flow<Set<WeekDay>> {
        return settingsAppManager.getProperty(
            workWeekDaysKey,
            WeekDay.weekDaySet.map { it.number.toString() }.toSet()
        ).map { it.map { number -> WeekDay.fromInt(number.toInt()) }.toSet() }
    }

    override suspend fun updateIsLimitTimeEnabled(state: Boolean) {
        settingsAppManager.writeProperty(isLimitTimeEnabledKey, state)
    }

    override suspend fun updateWorkTimeSpan(workTimeSpan: TimeSpan) {
        withContext(Dispatchers.IO) {
            settingsAppManager.writeProperty(startWorkKey, workTimeSpan.start)
            settingsAppManager.writeProperty(endWorkKey, workTimeSpan.end)
        }
    }

    override suspend fun updateWorkWeekDays(workDays: Set<WeekDay>) {
        val weekDayIds = workDays.map { it.number.toString() }.toSet()

        settingsAppManager.writeProperty(workWeekDaysKey, weekDayIds)
    }

    override val workConfigFlow: Flow<WorkTimeConfig>
        get() {
            val isLimitTimeEnabledFlow = getIsLimitTimeEnabled()
            val workTimeSpanFlow = getWorkTimeSpan()
            val workWeekDaysFlow = getWorkWeekDays()

            return combine(
                isLimitTimeEnabledFlow,
                workTimeSpanFlow,
                workWeekDaysFlow
            ) { isLimitTimeEnabled, workTime, workDays ->
                WorkTimeConfig(
                    isLimitTimeEnabled = isLimitTimeEnabled,
                    workTimeSpan = workTime,
                    workWeekDays = workDays.toPersistentSet()
                )
            }
        }
}