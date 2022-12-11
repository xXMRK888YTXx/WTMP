package com.xxmrk888ytxx.cryptomanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.random.Random
import kotlin.text.Charsets.UTF_8

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CryptoManagerTest {
    private val cryptoManager:CryptoManager = CryptoManagerImpl()


    @Test
    fun checkMultiCryptExpectMultiDecryptString() {
        val str1 = "wtrethkdthjoohdfho"
        val str2 = "fsekhhdhjhhy3"

        val eStr1 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str1.toByteArray()))
        val dStr1 = cryptoManager.decryptData(Base64.getDecoder().decode(eStr1)).toString(UTF_8)

        val eStr2 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str2.toByteArray()))
        val dStr2 = cryptoManager.decryptData(Base64.getDecoder().decode(eStr2)).toString(UTF_8)

        Assert.assertEquals(str1,dStr1)
        Assert.assertEquals(str2,dStr2)
    }

    @Test
    fun testEncryptIfStringsNotEqualsExpectEncryptNotEqualsStrings() {
        val str1 = "test".toByteArray()
        val str2 = "test2".toByteArray()

        val eStr1 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str1))
        val eStr2 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str2))

        Assert.assertNotEquals(str1,eStr1)
        Assert.assertNotEquals(str2,eStr2)
        Assert.assertNotEquals(eStr1,eStr2)
    }

    @Test
    fun testEncryptIfStringsEqualsExpectEncryptNotEqualsStrings() {
        val str1 = "test".toByteArray()
        val str2 = "test".toByteArray()

        val eStr1 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str1))
        val eStr2 = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str2))

        Assert.assertNotEquals(eStr1,eStr2)
    }

    @Test
    fun testDecrepitImputeStringExpectPrimaryString() {
        val str = "Mozart X".toByteArray()

        val eStr = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str))
        val dStr = cryptoManager.decryptData(Base64.getDecoder().decode(eStr))

        Assert.assertEquals(str.toString(UTF_8),dStr.toString(UTF_8))
    }


    @Test
    fun testDecrepitIfTextVeryLongExpectDecryptString() {
        val str = Random(System.currentTimeMillis()).nextBytes(1000).toString()

        val eStr = Base64.getEncoder().encodeToString(cryptoManager.encryptData(str.toByteArray()))
        val dStr = cryptoManager.decryptData(Base64.getDecoder().decode(eStr))

        Assert.assertEquals(str,dStr.toString(UTF_8))
    }

    @Test
    fun inputStringAndCryptAndDecryptExpectPrimaryString() {
        val testStr = "rwertewtr"
        val testStrByteArray = testStr.toByteArray()
        val cByteArray = cryptoManager.encryptData(testStrByteArray)
        val cBase64 = Base64.getEncoder().encodeToString(cByteArray)

        val cByteArrayBeforeDecrypt = Base64.getDecoder().decode(cBase64)
        val dByteArray = cryptoManager.decryptData(cByteArrayBeforeDecrypt)
        val dStr = dByteArray.toString(UTF_8)

        Assert.assertEquals(testStr,dStr)
    }

}