package com.xxmrk888ytxx.observer

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SucceededUnlockTrackedConfigManagerTest : BaseSettingsAppManagerTest() {

    private val succeededUnlockTrackedConfigChanger:SucceededUnlockTrackedConfigChanger by lazy {
        SucceededUnlockTrackedConfigManager(TestClass.settingsAppManager)
    }

    private val succeededUnlockTrackedConfigProvider:SucceededUnlockTrackedConfigProvider by lazy {
        SucceededUnlockTrackedConfigManager(TestClass.settingsAppManager)
    }

    @Test
    fun AlternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        var currentExpectedConfig = SucceededUnlockTrackedConfig(false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)
        //
        currentExpectedConfig = currentExpectedConfig.copy(isTracked = true)
        succeededUnlockTrackedConfigChanger.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(makePhoto = true)
        succeededUnlockTrackedConfigChanger.updateMakePhoto(currentExpectedConfig.makePhoto)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(joinPhotoToTelegramNotify = true)
        succeededUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(currentExpectedConfig.joinPhotoToTelegramNotify)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())

        succeededUnlockTrackedConfigChanger.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())
        //
        Assert.assertEquals(currentExpectedConfig,succeededUnlockTrackedConfigProvider.config.first())
    }
}