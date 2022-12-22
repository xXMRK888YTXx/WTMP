package com.xxmrk888ytxx.observer

import androidx.test.platform.app.InstrumentationRegistry
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager

/**
 * [Ru]
 * Класс в классе хранится объект класс [SettingsAppManager]
 * Для получения необходимо наследоваться от данного класса
 */
/**
 * [En]
 * The class in the class is stored object class [SettingsAppManager]
 * To get, you must inherit from this class
 */
internal open class BaseSettingsAppManagerTest {

    protected object SettingsAppManagerHolder {
        val context by lazy {InstrumentationRegistry.getInstrumentation().targetContext}
        val settingsAppManager: SettingsAppManager = SettingsAppManager(context,
            CryptoManagerImpl())
    }
}