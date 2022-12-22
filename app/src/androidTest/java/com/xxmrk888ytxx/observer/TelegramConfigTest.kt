package com.xxmrk888ytxx.observer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.TelegramConfig.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.observer.domain.TelegramConfig.TelegramConfigManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
internal class TelegramConfigTest : BaseSettingsAppManagerTest() {

    private val telegramConfigChanger:TelegramConfigChanger by lazy {
        TelegramConfigManager(TestClass.settingsAppManager)
    }

    private val telegramConfigProvider:TelegramConfigProvider by lazy {
        TelegramConfigManager(TestClass.settingsAppManager)
    }

    @org.junit.Test
    fun inputTelegramConfigExpectReturnTelegramConfig() = runBlocking(Dispatchers.IO) {
        val telegramConfig = TelegramConfig(235252,"rwtyugfdgfsafgnhmjk")

        telegramConfigChanger.updateTelegramConfig(telegramConfig)

        val restoredTelegramConfig = telegramConfigProvider.telegramConfig.first()

        Assert.assertEquals(telegramConfig,restoredTelegramConfig)
    }

//    @org.junit.Test
//    fun testCheckIsExistTelegramConfigFun() = runBlocking(Dispatchers.IO) {
//        println(Test.settingsAppManager)
//        val telegramConfig = TelegramConfig(235252,"rwtyugfdgfsafgnhmjk")
//
//        Assert.assertEquals(false,Test.settingsAppManager.isConfigExist().first())
//
//        settingsAppManager.updateTelegramConfig(telegramConfig)
//
//        Assert.assertEquals(true,settingsAppManager.isConfigExist().first())
//    }
}