package com.xxmrk888ytxx.coredeps

import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import org.junit.Assert
import org.junit.Test

class ExtensionTest {

    @Test
    fun testParsToJsonTelegramConfigAndBackToClassExpectClassIsEquals() {
        val telegramConfig = TelegramConfig(4325243,"4353tyrhgfdvc")

        val jsonString = toJson(telegramConfig)

        val classFromJson = fromJson(jsonString,TelegramConfig::class.java)

        Assert.assertEquals(telegramConfig,classFromJson)
    }

    @Test
    fun parseNullStringExpectNullModel() {
        val jsonString:String? = null

        val classFromJson = fromJson(jsonString,TelegramConfig::class.java)

        Assert.assertEquals(null,classFromJson)
    }
}