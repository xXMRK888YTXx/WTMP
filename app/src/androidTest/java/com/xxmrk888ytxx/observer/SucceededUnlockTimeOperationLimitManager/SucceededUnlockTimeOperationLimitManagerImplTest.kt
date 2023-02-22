package com.xxmrk888ytxx.observer.SucceededUnlockTimeOperationLimitManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.observer.BaseSettingsAppManagerTest
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.SucceededUnlockTimeOperationLimitManagerImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class SucceededUnlockTimeOperationLimitManagerImplTest : BaseSettingsAppManagerTest() {
    private val limitManager: TimeOperationLimitManager<Nothing> by lazy {
        SucceededUnlockTimeOperationLimitManagerImpl(SettingsAppManagerHolder.settingsAppManager)
    }



    @Before
    fun init() = runBlocking {
        limitManager.disableLimit()
    }

    @Test
    fun setupLimitAndCheckThemStateAndCheckAfterLimitTimeExceptFirstCheckIsTrueTwoCheckIsFalse() = runBlocking {
        val time = 100

        repeat(2) {
            limitManager.enableLimit(time)

            Assert.assertEquals(true,limitManager.isLimitEnable(time))

            delay(time.toLong())

            Assert.assertEquals(false,limitManager.isLimitEnable(time))
        }
    }

    @Test
    fun setupLimitAfterRepeatThemExpectSecondaryLimitNotSetup() = runBlocking {
        val time = 1000
        limitManager.enableLimit(time)

        Assert.assertEquals(true,limitManager.isLimitEnable(time))

        limitManager.isLimitEnable(time * 1000)

        delay(time.toLong())

        Assert.assertEquals(false,limitManager.isLimitEnable(time))
    }

    @Test
    fun setupLimitAndCheckThemStateAfterCheckLimitWithOtherTimeExpectFirstCheckIsTrueSecondIsFalse() = runBlocking {
        val time = 100
        val time2 = 200

        limitManager.enableLimit(time)

        Assert.assertEquals(true,limitManager.isLimitEnable(time))
        Assert.assertEquals(false,limitManager.isLimitEnable(time2))
    }

    @Test
    fun setupTwoLimitExpectOnlySecondLimitReplaceFirst() = runBlocking {
        val time = 100
        val time2 = 500

        limitManager.enableLimit(time)
        limitManager.enableLimit(time2)

        delay(300)
        Assert.assertEquals(true,limitManager.isLimitEnable(time2))
        delay(300)

        Assert.assertEquals(false,limitManager.isLimitEnable(time2))
    }

    @Test
    fun setupLimitAndDisableThisAndCheckThemExpectCheckReturnFalse() = runBlocking {
        val time = 10000

        limitManager.enableLimit(time)
        Assert.assertEquals(true,limitManager.isLimitEnable(time))
        limitManager.disableLimit()

        Assert.assertEquals(false,limitManager.isLimitEnable(time))
    }
}