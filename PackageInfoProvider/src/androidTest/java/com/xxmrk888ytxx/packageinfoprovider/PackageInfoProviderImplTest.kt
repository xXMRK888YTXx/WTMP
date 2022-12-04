package com.xxmrk888ytxx.packageinfoprovider

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * [Ru]
 * Данный класс является тестами для класс [PackageInfoProviderImpl]
 * Для коректного тестирования должно быть установлено основное приложение
 * (packageName com.xxmrk888ytxx.observer)
 */

/**
 * [En]
 * This class is a test for the [PackageInfoProviderImpl] class
 * For correct testing, the main application must be installed
 * (packageName com.xxmrk888ytxx.observer)
 */
@RunWith(AndroidJUnit4::class)
class PackageInfoProviderImplTest {
    private val packageInfoProvider: PackageInfoProvider by lazy {
        PackageInfoProviderImpl(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    private val mainAppPackageName = "com.xxmrk888ytxx.observer"
    private val mainAppName = "Observer"

    @Test
    fun getMainApplicationNameExpectGetName() {
        val appName = packageInfoProvider.getAppName(mainAppPackageName)

        assertEquals(mainAppName,appName)
    }

    @Test
    fun getMainApplicationIconExpectGetIcon() {
        val appIcon = packageInfoProvider.getAppIcon(mainAppPackageName)

        assertNotEquals(null,appIcon)
    }

    @Test
    fun getNonExistentApplicationNameExpectReturnsNull() {
        val appName = packageInfoProvider.getAppName("app not exist")

        assertEquals(appName,null)
    }

    @Test
    fun getNonExistentApplicationIconExpectReturnsNull() {
        val appIcon = packageInfoProvider.getAppIcon("app not exist")

        assertEquals(appIcon,null)
    }
}