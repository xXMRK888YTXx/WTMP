package com.xxmrk888ytxx.observer

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FailedUnlockTrackedConfigTest : BaseSettingsAppManagerTest() {

    private val failedUnlockTrackedConfigChanger:FailedUnlockTrackedConfigChanger by lazy {
        TestClass.settingsAppManager
    }

    private val failedUnlockTrackedConfigProvider:FailedUnlockTrackedConfigProvider by lazy {
        TestClass.settingsAppManager
    }

    @Before
    fun init() = runBlocking {
        failedUnlockTrackedConfigChanger.updateIsTracked(false)
        failedUnlockTrackedConfigChanger.updateMakePhoto(false)
        failedUnlockTrackedConfigChanger.updateNotifyInTelegram(false)
        failedUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(false)
    }

    @Test
    fun AlternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        var currentExpectedConfig = FailedUnlockTrackedConfig(false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)
        //
        currentExpectedConfig = currentExpectedConfig.copy(isTracked = true)
        failedUnlockTrackedConfigChanger.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(makePhoto = true)
        failedUnlockTrackedConfigChanger.updateMakePhoto(currentExpectedConfig.makePhoto)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        failedUnlockTrackedConfigChanger.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(joinPhotoToTelegramNotify = true)
        failedUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(currentExpectedConfig.joinPhotoToTelegramNotify)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())

        failedUnlockTrackedConfigChanger.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        failedUnlockTrackedConfigChanger.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())
        //
        Assert.assertEquals(currentExpectedConfig,failedUnlockTrackedConfigProvider.config.first())
    }
}