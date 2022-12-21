package com.xxmrk888ytxx.observer

import androidx.test.platform.app.InstrumentationRegistry
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager


open class BaseSettingsAppManagerTest {

    protected object TestClass {
        val context by lazy {InstrumentationRegistry.getInstrumentation().targetContext}
        val settingsAppManager: SettingsAppManager = SettingsAppManager(context,
            CryptoManagerImpl())
    }
}