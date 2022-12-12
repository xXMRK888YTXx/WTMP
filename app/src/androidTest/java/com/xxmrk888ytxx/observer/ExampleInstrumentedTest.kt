package com.xxmrk888ytxx.observer

import android.content.Context
import android.content.ContextWrapper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SettingsAppManagerText {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    object Test {
        private val context = InstrumentationRegistry.getInstrumentation().targetContext
        val settingsAppManager:SettingsAppManager = SettingsAppManager(context,CryptoManagerImpl())
    }

    @After
    fun clear() {
        val contextWrapper = ContextWrapper(context)
        val rootDir = contextWrapper.getDir("files", Context.MODE_PRIVATE)
        rootDir.deleteRecursively()
    }

    @org.junit.Test
    fun inputTelegramConfigExpectReturnTelegramConfig() = runBlocking(Dispatchers.IO) {
        println(Test.settingsAppManager)
        val telegramConfig = TelegramConfig(235252,"rwtyugfdgfsafgnhmjk")

        Test.settingsAppManager.updateTelegramConfig(telegramConfig)

        val restoredTelegramConfig = Test.settingsAppManager.getTelegramConfig().first()

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