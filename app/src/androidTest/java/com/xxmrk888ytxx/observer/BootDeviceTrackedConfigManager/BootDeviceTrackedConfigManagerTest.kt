package com.xxmrk888ytxx.observer.BootDeviceTrackedConfigManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig.BootDeviceTrackedConfigProvider
import com.xxmrk888ytxx.coredeps.models.BootDeviceTrackedConfig
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.BootDeviceTrackedConfig.BootDeviceTrackedConfigManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class BootDeviceTrackedConfigManagerTest : BaseSettingsAppManagerTest() {
    private val changer: BootDeviceTrackedConfigChanger by lazy {
        BootDeviceTrackedConfigManager(SettingsAppManagerHolder.settingsAppManager)
    }

    private val provider: BootDeviceTrackedConfigProvider by lazy {
        BootDeviceTrackedConfigManager(SettingsAppManagerHolder.settingsAppManager)
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
        var currentExpectedConfig = BootDeviceTrackedConfig(false,
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
        val expectedConfig = BootDeviceTrackedConfig(isTracked = false,
            makePhoto = false,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false)

        changer.updateIsTracked(true)
        changer.updateMakePhoto(true)
        changer.updateNotifyInTelegram(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        Assert.assertEquals(
            provider.config.first(),
            BootDeviceTrackedConfig(isTracked = true,
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
        Assert.assertEquals(BootDeviceTrackedConfig(isTracked = true,
            makePhoto = false,
            notifyInTelegram = true,
            joinPhotoToTelegramNotify = false),
            provider.config.first())
        changer.updateMakePhoto(true)
        changer.updateJoinPhotoToTelegramNotify(true)
        changer.updateNotifyInTelegram(false)

        Assert.assertEquals(BootDeviceTrackedConfig(isTracked = true,
            makePhoto = true,
            notifyInTelegram = false,
            joinPhotoToTelegramNotify = false),provider.config.first())
    }

}