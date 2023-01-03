package com.xxmrk888ytxx.observer.AppPasswordManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.AppPasswordManager.AppPasswordManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AppPasswordManagerTest : BaseSettingsAppManagerTest() {

    private val appPasswordManager = AppPasswordManager(SettingsAppManagerHolder.settingsAppManager,CryptoManagerImpl())

    private val provider:AppPasswordProvider by lazy {
        appPasswordManager
    }

    private val changer:AppPasswordChanger by lazy {
        appPasswordManager
    }

    private val testPassword = "1111"

    @Before
    fun init() = runBlocking {
        try {
            changer.setupAppPassword(null,testPassword)
        }catch (_:Exception) {}
    }

    @Test
    fun checkValidPasswordExpectCheckingReturnsTrue() = runBlocking {
        Assert.assertEquals(true,provider.isAppPassword(testPassword))
    }

    @Test
    fun checkInValidPasswordExpectCheckingReturnsFalse() = runBlocking {
        Assert.assertEquals(false,provider.isAppPassword(testPassword + "1"))
    }

    @Test
    fun checkPasswordSetupExpectReturnsTrue() = runBlocking {
        Assert.assertEquals(true,provider.isPasswordSetup().first())
    }

    @Test
    fun setupNewPasswordExpectNewPasswordIsValid() = runBlocking {
        val newPassword = "@2222"
        Assert.assertEquals(true,provider.isAppPassword(testPassword))

        changer.setupAppPassword(testPassword,newPassword)

        Assert.assertEquals(true,provider.isAppPassword(newPassword))
        Assert.assertEquals(false,provider.isAppPassword(testPassword))

        changer.setupAppPassword(newPassword,testPassword)
        Assert.assertEquals(true,provider.isAppPassword(testPassword))
    }
}
