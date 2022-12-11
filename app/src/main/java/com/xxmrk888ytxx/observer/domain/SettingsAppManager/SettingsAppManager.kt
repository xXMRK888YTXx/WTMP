package com.xxmrk888ytxx.observer.domain.SettingsAppManager

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TelegramConfigProvider
import com.xxmrk888ytxx.coredeps.models.TelegramConfig
import javax.inject.Inject

class SettingsAppManager @Inject constructor(
    private val context: Context,
    private val cryptoManager: CryptoManager
) : TelegramConfigChanger,TelegramConfigProvider {
    override suspend fun updateTelegramConfig(telegramConfig: TelegramConfig) {
        TODO("Not yet implemented")
    }

    override fun getTelegramConfig() {
        TODO("Not yet implemented")
    }

    override fun isConfigExist(): Boolean {
        TODO("Not yet implemented")
    }


}