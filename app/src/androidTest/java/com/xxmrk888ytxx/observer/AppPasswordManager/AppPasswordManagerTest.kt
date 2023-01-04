package com.xxmrk888ytxx.observer.AppPasswordManager

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.AppPasswordManager.AppPasswordManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
            changer.removePassword(testPassword)
        }catch (_:Exception) {}
    }

    @After
    fun clear() = runBlocking {
        try {
            changer.removePassword(testPassword)
        }catch (_:Exception) {}
    }

    @Test
    fun setupPasswordAndCheckThemExpectCheckPasswordReturnsTrue() = runBlocking {
        Assert.assertEquals(false,provider.isPasswordSetup().first())
        
        changer.setupAppPassword(testPassword)
        
        Assert.assertEquals(true,provider.isAppPassword(testPassword))
    }

    @Test
    fun setupPasswordAndCheckIncorrectPasswordExpectCheckPasswordReturnsFalse() = runBlocking {
        Assert.assertEquals(false,provider.isPasswordSetup().first())

        changer.setupAppPassword(testPassword)

        Assert.assertEquals(false,provider.isAppPassword(testPassword+"1"))
    }
    
    @Test
    fun checkPasswordStateExpectReturnsFalse() = runBlocking {
        Assert.assertEquals(false,provider.isPasswordSetup().first())
    }

    @Test
    fun setupAndCheckPasswordStateExpectReturnsTrue() = runBlocking {
        changer.setupAppPassword(testPassword)
        Assert.assertEquals(true,provider.isPasswordSetup().first())
    }
    
    @Test(expected = IllegalStateException::class)
    fun setupPasswordRepeatedlyExpectThrowException() = runBlocking {
        changer.setupAppPassword(testPassword)
        changer.setupAppPassword(testPassword)
    }

    @Test(expected = IllegalArgumentException::class)
    fun setupPasswordAndRemoveThisWithIncorrectPasswordExpectThrowException() = runBlocking {
        changer.setupAppPassword(testPassword)

        changer.removePassword(testPassword+"1")
    }

    @Test(expected = IllegalStateException::class)
    fun checkPasswordWithoutThisSetupExpectThrowException(): Unit = runBlocking {
        provider.isAppPassword(testPassword)
    }

    @Test
    fun addPasswordChangerThemAndRemoveExpectIsPasswordSetupReturnsFalse() = runBlocking {
        val testPassword2 = testPassword + "2"
        changer.setupAppPassword(testPassword)
        Assert.assertEquals(true,provider.isAppPassword(testPassword))

        changer.removePassword(testPassword)
        changer.setupAppPassword(testPassword2)
        Assert.assertEquals(false,provider.isAppPassword(testPassword))
        Assert.assertEquals(true,provider.isAppPassword(testPassword2))
        changer.removePassword(testPassword2)

        Assert.assertEquals(false,provider.isPasswordSetup().first())
    }
    
}
