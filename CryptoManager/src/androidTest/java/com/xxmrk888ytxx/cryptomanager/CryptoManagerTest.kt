package com.xxmrk888ytxx.cryptomanager

import android.util.Base64
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
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
        val list = mutableListOf<String>()
        repeat(10) {
            list.add(Random(System.currentTimeMillis()).nextLong().toString())
        }

        val cryptList = list.map { Base64.encodeToString(cryptoManager.encryptData(it.toByteArray()),0) }
        val decryptList = cryptList.map { cryptoManager.decryptData(Base64.decode(it,0)).toString(UTF_8) }

        Assert.assertEquals(list,decryptList)
    }

    @Test
    fun testEncryptIfStringsNotEqualsExpectEncryptNotEqualsStrings() {
        val str1 = "test".toByteArray()
        val str2 = "test2".toByteArray()

        val eStr1 = Base64.encodeToString(cryptoManager.encryptData(str1),0)
        val eStr2 = Base64.encodeToString(cryptoManager.encryptData(str2),0)

        Assert.assertNotEquals(str1,eStr1)
        Assert.assertNotEquals(str2,eStr2)
        Assert.assertNotEquals(eStr1,eStr2)
    }

    @Test
    fun testEncryptIfStringsEqualsExpectEncryptNotEqualsStrings() {
        val str1 = "test".toByteArray()
        val str2 = "test".toByteArray()

        val eStr1 = Base64.encodeToString(cryptoManager.encryptData(str1),9)
        val eStr2 = Base64.encodeToString(cryptoManager.encryptData(str2),9)

        Assert.assertNotEquals(eStr1,eStr2)
    }

    @Test
    fun testDecrepitImputeStringExpectPrimaryString() {
        val str = "Mozart X".toByteArray()

        val eStr = Base64.encodeToString(cryptoManager.encryptData(str),0)
        val dStr = cryptoManager.decryptData(Base64.decode(eStr,0))

        Assert.assertEquals(str.toString(UTF_8),dStr.toString(UTF_8))
    }


    @Test
    fun testDecrepitIfTextVeryLongExpectDecryptString() {
        val str = Random(System.currentTimeMillis()).nextBytes(1000).toString()

        val eStr = Base64.encodeToString(cryptoManager.encryptData(str.toByteArray()),0)
        val dStr = cryptoManager.decryptData(Base64.decode(eStr,0))

        Assert.assertEquals(str,dStr.toString(UTF_8))
    }

    @Test
    fun inputStringAndCryptAndDecryptExpectPrimaryString() {
        val testStr = "rwertewtr"
        val testStrByteArray = testStr.toByteArray()
        val cByteArray = cryptoManager.encryptData(testStrByteArray)
        val cBase64 = Base64.encodeToString(cByteArray,0)

        val cByteArrayBeforeDecrypt = Base64.decode(cBase64,0)
        val dByteArray = cryptoManager.decryptData(cByteArrayBeforeDecrypt)
        val dStr = dByteArray.toString(UTF_8)

        Assert.assertEquals(testStr,dStr)
    }
}