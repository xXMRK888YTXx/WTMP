package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface TelegramConfigProvider {
    fun getTelegramConfig()

    fun isConfigExist() : Boolean
}