package com.xxmrk888ytxx.observer.AppOpenTimeOperationLimitManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.AppOpenTimeLimitRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.models.AppOpenTimeLimitModel
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.AppOpenTimeOperationLimitManagerImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AppOpenTimeOperationLimitManagerImplTest {

    private val inMemoryDatabase = mutableMapOf<String,AppOpenTimeLimitModel>()

    private val repo = mockk<AppOpenTimeLimitRepository>(relaxed = true)

    private val limitManager:TimeOperationLimitManager<String> by lazy {
        AppOpenTimeOperationLimitManagerImpl(repo)
    }

    @Before
    fun init() {
        coEvery { repo.addLimit(any()) } answers {
            val model = this.args[0] as AppOpenTimeLimitModel

            inMemoryDatabase.put(model.packageName,model)
        }

        coEvery { repo.getTimeLimitForApp(any()) } coAnswers {
            val packageName = this.args[0] as String

            inMemoryDatabase.get(packageName)
        }

        coEvery { repo.removeLimit(any()) } answers {
            val packageName = this.args[0] as String

            inMemoryDatabase.remove(packageName)
        }
    }

    @After
    fun clear() {
        inMemoryDatabase.clear()
    }

    @Test
    fun setupLimitAndCheckThemStateAndCheckAfterLimitTimeExceptFirstCheckIsTrueTwoCheckIsFalse() = runBlocking {
        val time = 100
        val packageName = "test"

        repeat(2) {
            limitManager.enableLimit(time,packageName)


            Assert.assertEquals(true,limitManager.isLimitEnable(time,packageName))

            delay(time.toLong())

            Assert.assertEquals(false,limitManager.isLimitEnable(time,packageName))
        }
    }

    @Test
    fun setupFirstLimitAndSetupSecondLimitOnOnePackageNameExpectSecondLimitTimeReplaceFirstLimitTime() = runBlocking {
        val time = 100
        val packageName = "test"
        limitManager.enableLimit(time,packageName)

        val databaseData = inMemoryDatabase.get(packageName)
        Assert.assertNotNull(databaseData)

        limitManager.enableLimit(time * 1000,packageName)

        val databaseData2 = inMemoryDatabase.get(packageName)
        Assert.assertNotNull(databaseData2)

        Assert.assertNotEquals(databaseData,databaseData2)
    }

    @Test
    fun setupTwoLimitWithDifferentPeriodExpectAfterCheckWithTime2LimitCancel() = runBlocking {
        val time = 100
        val time2 = 200
        val packageName = "test"

        limitManager.enableLimit(time,packageName)

        Assert.assertEquals(true,limitManager.isLimitEnable(time,packageName))
        Assert.assertEquals(false,limitManager.isLimitEnable(time2,packageName))
        Assert.assertEquals(false,limitManager.isLimitEnable(time,packageName))

    }

    @Test
    fun AddLimitAndCancelThemExpectTheyCancel() = runBlocking {
        val time = 100
        val packageName = "test"
        limitManager.enableLimit(time,packageName)
        Assert.assertEquals(true,limitManager.isLimitEnable(time,packageName))

        limitManager.disableLimit(packageName)

        Assert.assertEquals(false,limitManager.isLimitEnable(time,packageName))

    }
}