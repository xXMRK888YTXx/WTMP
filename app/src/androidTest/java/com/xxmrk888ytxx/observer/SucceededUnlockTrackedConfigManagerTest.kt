package com.xxmrk888ytxx.observer

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import com.xxmrk888ytxx.observer.domain.SucceededUnlockTrackedConfig.SucceededUnlockTrackedConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class SucceededUnlockTrackedConfigManagerTest : BaseSettingsAppManagerTest() {

    private val succeededUnlockTrackedConfigChanger:SucceededUnlockTrackedConfigChanger by lazy {
        SucceededUnlockTrackedConfigManager(TestClass.settingsAppManager)
    }

    private val succeededUnlockTrackedConfigProvider:SucceededUnlockTrackedConfigProvider by lazy {
        SucceededUnlockTrackedConfigManager(TestClass.settingsAppManager)
    }

    @Before
    fun init() = runBlocking {
        succeededUnlockTrackedConfigChanger.updateIsTracked(false)
        succeededUnlockTrackedConfigChanger.updateMakePhoto(false)
        succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(false)
        succeededUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(false)
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

    @Test
    fun installAllParamsTrueAndInstallIsTrackedParamFalseExpectAllParamsIsFalse() = runBlocking {
        val expectedConfig = SucceededUnlockTrackedConfig(isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

        succeededUnlockTrackedConfigChanger.updateIsTracked(true)
        succeededUnlockTrackedConfigChanger.updateMakePhoto(true)
        succeededUnlockTrackedConfigChanger.updateNotifyInTelegram(true)
        succeededUnlockTrackedConfigChanger.updateJoinPhotoToTelegramNotify(true)
        Assert.assertEquals(
            succeededUnlockTrackedConfigProvider.config.first(),
            SucceededUnlockTrackedConfig(isTracked = true,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = true)
        )
        succeededUnlockTrackedConfigChanger.updateIsTracked(false)

        Assert.assertEquals(expectedConfig,succeededUnlockTrackedConfigProvider.config.first())
    }
}