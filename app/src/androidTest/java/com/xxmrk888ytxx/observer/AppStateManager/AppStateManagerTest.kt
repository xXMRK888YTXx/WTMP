package com.xxmrk888ytxx.observer.AppStateManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.AppStateManager.AppStateManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AppStateManagerTest : BaseSettingsAppManagerTest() {

    private val changer: AppStateChanger by lazy {
        AppStateManager(SettingsAppManagerHolder.settingsAppManager)
    }

    private val provider: AppStateProvider by lazy {
        AppStateManager(SettingsAppManagerHolder.settingsAppManager)
    }

    @Before
    fun init() = runBlocking {
        changer.updateAppState(false)
    }

    @Test
    fun AlternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        var currentExpectedState = false
        //
        Assert.assertEquals(currentExpectedState,provider.isAppEnable.first())

        currentExpectedState = true
        changer.updateAppState(currentExpectedState)
        Assert.assertEquals(currentExpectedState,provider.isAppEnable.first())

        currentExpectedState = false
        changer.updateAppState(currentExpectedState)
        Assert.assertEquals(currentExpectedState,provider.isAppEnable.first())
    }

}