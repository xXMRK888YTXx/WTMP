package com.xxmrk888ytxx.observer.FailedUnlockTrackedConfigManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.FailedUnlockTrackedConfig.FailedUnlockTrackedConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class FailedUnlockTrackedConfigManagerTest : BaseSettingsAppManagerTest() {

    private val changer:FailedUnlockTrackedConfigChanger by lazy {
        FailedUnlockTrackedConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    private val provider:FailedUnlockTrackedConfigProvider by lazy {
        FailedUnlockTrackedConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    @Before
    fun init() = runBlocking {
        changer.updateIsTracked(false)
        changer.updateTimeOperationLimit(0)
        changer.updateMakePhoto(false)
        changer.updateNotifyInTelegram(false)
        changer.updateJoinPhotoToTelegramNotify(false)
        changer.updateCountFailedUnlockToTrigger(1)
    }

    @Test
    fun AlternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        var currentExpectedConfig = FailedUnlockTrackedConfig(false,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)
        //
        currentExpectedConfig = currentExpectedConfig.copy(isTracked = true)
        changer.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(timeOperationLimit = 3)
        changer.updateTimeOperationLimit(currentExpectedConfig.timeOperationLimit)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(countFailedUnlockToTrigger = 8)
        changer.updateCountFailedUnlockToTrigger(currentExpectedConfig.countFailedUnlockToTrigger)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(makePhoto = true)
        changer.updateMakePhoto(currentExpectedConfig.makePhoto)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        changer.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(joinPhotoToTelegramNotify = true)
        changer.updateJoinPhotoToTelegramNotify(currentExpectedConfig.joinPhotoToTelegramNotify)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        changer.updateIsTracked(currentExpectedConfig.isTracked)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())

        currentExpectedConfig = currentExpectedConfig.copy(notifyInTelegram = true)
        changer.updateNotifyInTelegram(currentExpectedConfig.notifyInTelegram)
        Assert.assertEquals(currentExpectedConfig,provider.config.first())
        //
        Assert.assertEquals(currentExpectedConfig,provider.config.first())
    }

    @Test
    fun installAllParamsTrueAndInstallIsTrackedParamFalseExpectAllParamsIsFalse() = runBlocking {
        val expectedConfig = FailedUnlockTrackedConfig(isTracked = false,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

        changer.updateIsTracked(true)
        changer.updateMakePhoto(true)
        changer.updateNotifyInTelegram(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        Assert.assertEquals(
            provider.config.first(),
            FailedUnlockTrackedConfig(isTracked = true,
                timeOperationLimit = 0,
                countFailedUnlockToTrigger = 1,
                makePhoto = true,
                notifyInTelegram = true,
                joinPhotoToTelegramNotify = true)
        )
        changer.updateIsTracked(false)

        Assert.assertEquals(expectedConfig,provider.config.first())
    }

    @Test
    fun setFalseMakePhotoAndNotifyInTelegramExpectJoinPhotoToTelegramNotifySetFalseToo() = runBlocking {
        changer.updateIsTracked(true)
        changer.updateMakePhoto(true)
        changer.updateNotifyInTelegram(true)
        changer.updateJoinPhotoToTelegramNotify(true)

        changer.updateMakePhoto(false)
        Assert.assertEquals(FailedUnlockTrackedConfig(isTracked = true,
            countFailedUnlockToTrigger = 1,
            timeOperationLimit = 0,
            makePhoto = false,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false),
            provider.config.first())
        changer.updateMakePhoto(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        changer.updateNotifyInTelegram(false)

        Assert.assertEquals(FailedUnlockTrackedConfig(isTracked = true,
            timeOperationLimit = 0,
            countFailedUnlockToTrigger = 1,
            makePhoto = true,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false),provider.config.first())
    }
}