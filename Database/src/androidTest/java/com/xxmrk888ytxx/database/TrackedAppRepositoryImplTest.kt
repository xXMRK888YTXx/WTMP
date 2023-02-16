@file:OptIn(ExperimentalCoroutinesApi::class)

package com.xxmrk888ytxx.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Dao.*
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrackedAppRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDataBase

    private val context by lazy {
        ApplicationProvider.getApplicationContext<Context>()
    }

    private val repo = spyk(TrackedAppRepositoryImpl(context))

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()

        val dataBaseComponent = object : DataBaseComponent {
            override val deviceEventDao: DeviceEventDao = database.getDeviceEventDao()

            override val appOpenEventDao: AppOpenEventDao = database.getAppOpenEventDao()

            override val unlockDeviceEvent: UnlockDeviceEvent = database.getUnlockDeviceEventDao()

            override val trackedAppDao: TrackedAppDao = database.getTrackedAppDao()
            override val appOpenTimeLimitDao: AppOpenTimeLimitDao
                get() = TODO("Not yet implemented")
        }
        mockkStatic(DaggerDataBaseComponent::class)
        every { DaggerDataBaseComponent.factory().create(context) } returns dataBaseComponent
    }

    @After
    fun close() {
        database.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addPackageNamesInDBExpectReturnsTheyPackagesNames() = runTest {
        val testList = getTestPackageNames()

        testList.forEach {
            repo.addTrackedPackageName(it)
        }

        Assert.assertEquals(testList.toList(),repo.getAllTrackedPackageNames().first())
    }

    @Test
    fun addPackagesNameRemoveFirstAndLastExpectReturnsListWithoutRemoveItems() = runTest {
        val testList = getTestPackageNames()

        testList.forEach {
            repo.addTrackedPackageName(it)
        }

        repo.removeTrackedPackageName(testList[0])
        repo.removeTrackedPackageName(testList[testList.lastIndex])

        Assert.assertEquals(testList.drop(1).dropLast(1),repo.getAllTrackedPackageNames().first())
    }

    private fun getTestPackageNames() : List<String> {
        return listOf("dsfghf","erdtgh","efsghkj","fhdfhuhvudvhuhcv","fhdxgvihcxubf")
    }
}