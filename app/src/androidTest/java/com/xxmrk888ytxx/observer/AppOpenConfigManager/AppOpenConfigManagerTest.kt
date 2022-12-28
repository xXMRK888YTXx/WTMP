package com.xxmrk888ytxx.observer.AppOpenConfigManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig.AppOpenConfigProvider
import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.AppOpenConfig.AppOpenConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AppOpenConfigManagerTest : BaseSettingsAppManagerTest() {
    private val changer: AppOpenConfigChanger by lazy {
        AppOpenConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    private val provider: AppOpenConfigProvider by lazy {
        AppOpenConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    @Before
    fun init() = runBlocking {
        changer.updateIsTracked(false)
        changer.updateMakePhoto(false)
        changer.updateNotifyInTelegram(false)
        changer.updateJoinPhotoToTelegramNotify(false)
    }

    @Test
    fun AlternatelyChangeConfigParamsExpectConfigInFlowEquelsExpectedConfig() = runBlocking {
        var currentExpectedConfig = AppOpenConfig(false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)
        //
        currentExpectedConfig = currentExpectedConfig.copy(isTracked = true)
        changer.updateIsTracked(currentExpectedConfig.isTracked)
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
        val expectedConfig = AppOpenConfig(isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

        changer.updateIsTracked(true)
        changer.updateMakePhoto(true)
        changer.updateNotifyInTelegram(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        Assert.assertEquals(
            provider.config.first(),
            AppOpenConfig(isTracked = true,
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
        Assert.assertEquals(AppOpenConfig(isTracked = true,
            makePhoto = false,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false),
            provider.config.first())
        changer.updateMakePhoto(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        changer.updateNotifyInTelegram(false)

        Assert.assertEquals(AppOpenConfig(isTracked = true,
            makePhoto = true,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false),provider.config.first())
    }
}