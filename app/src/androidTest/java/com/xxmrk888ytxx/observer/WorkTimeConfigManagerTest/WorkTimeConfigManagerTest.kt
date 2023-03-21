package com.xxmrk888ytxx.observer.WorkTimeConfigManagerTest

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig.WorkTimeConfigProvider
import com.xxmrk888ytxx.coredeps.models.TimeSpan
import com.xxmrk888ytxx.coredeps.models.WeekDay
import com.xxmrk888ytxx.coredeps.models.WorkTimeConfig
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.WorkTimeConfig.WorkTimeConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class WorkTimeConfigManagerTest : BaseSettingsAppManagerTest() {

    private val provider:WorkTimeConfigProvider by lazy {
        WorkTimeConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    private val changer:WorkTimeConfigChanger by lazy {
        WorkTimeConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    @Before
    fun init() = runBlocking {
        changer.updateIsLimitTimeEnabled(false)
        changer.updateWorkTimeSpan(TimeSpan.NO_SETUP)
        changer.updateWorkWeekDays(WeekDay.weekDaySet)
    }

    @Test
    fun alternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        suspend fun WorkTimeConfigProvider.getConfig() : WorkTimeConfig {
            return this.workConfigFlow.first()
        }

        var expectedConfig = WorkTimeConfig()
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(isLimitTimeEnabled = true)
        changer.updateIsLimitTimeEnabled(expectedConfig.isLimitTimeEnabled)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(isLimitTimeEnabled = false)
        changer.updateIsLimitTimeEnabled(expectedConfig.isLimitTimeEnabled)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(workTimeSpan = TimeSpan(111,-1))
        changer.updateWorkTimeSpan(expectedConfig.workTimeSpan)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(workTimeSpan = TimeSpan(111,222))
        changer.updateWorkTimeSpan(expectedConfig.workTimeSpan)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(workTimeSpan = TimeSpan(-1,-1))
        changer.updateWorkTimeSpan(expectedConfig.workTimeSpan)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(workWeekDays = setOf(WeekDay.Sunday,WeekDay.Monday))
        changer.updateWorkWeekDays(expectedConfig.workWeekDays)
        Assert.assertEquals(expectedConfig,provider.getConfig())

        expectedConfig = expectedConfig.copy(workWeekDays = WeekDay.weekDaySet)
        changer.updateWorkWeekDays(expectedConfig.workWeekDays)
        Assert.assertEquals(expectedConfig,provider.getConfig())
    }
}