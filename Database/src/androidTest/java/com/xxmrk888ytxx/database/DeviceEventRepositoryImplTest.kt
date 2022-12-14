@file:OptIn(ExperimentalCoroutinesApi::class)

package com.xxmrk888ytxx.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Dao.AppOpenEventDao
import com.xxmrk888ytxx.database.Dao.DeviceEventDao
import com.xxmrk888ytxx.database.Dao.UnlockDeviceEvent
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class DeviceEventRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database:AppDataBase

    private val context by lazy {
        ApplicationProvider.getApplicationContext<Context>()
    }

    private val repo = spyk(DeviceEventRepositoryImpl(context))

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(context,AppDataBase::class.java).build()

        val dataBaseComponent = object : DataBaseComponent {
            override val deviceEventDao: DeviceEventDao = database.getDeviceEventDao()

            override val appOpenEventDao: AppOpenEventDao = database.getAppOpenEventDao()

            override val unlockDeviceEvent: UnlockDeviceEvent = database.getUnlockDeviceEventDao()

        }

        mockkStatic(DaggerDataBaseComponent::class)
        every { DaggerDataBaseComponent.factory().create(context) } returns dataBaseComponent
    }

    @After
    fun close() {
        database.close()
    }

    @Test
    fun addEventListInDBEExpectReturnsEqualsList() = runTest {
        val testList = getTestEventList()
        testList.forEach { repo.addEvent(it) }

        val listFromDB = repo.getAllEvents().first()

        Assert.assertEquals(testList,listFromDB)
    }

    @Test
    fun addListInDBAndRemoveFirstThreeItemExpectedReturnsListWithoutRemovedItems() = runTest {
        val testList = getTestEventList()
        testList.forEach { repo.addEvent(it) }

        val expectedList = testList.drop(3)
        repo.removeEvent(testList[2].eventId)
        repo.removeEvent(testList[1].eventId)
        repo.removeEvent(testList[0].eventId)
        val listFromDB = repo.getAllEvents().first()

        Assert.assertEquals(expectedList,listFromDB)
    }

    @Test
    fun runTwoCoroutinesToAddEventsToDBEExpectTheAllHasBeenAdded() = runBlocking {
        val testList = getTestEventList()
        val firstScope = CoroutineScope(Job() + Dispatchers.IO)
        val secondScope = CoroutineScope(Job() + Dispatchers.IO)
        var isFirstPartLoad = false
        var isSecondPartLoad = false
        testList.forEachIndexed {index,it ->
            firstScope.launch {
                repo.addEvent(it)
                if(index == testList.lastIndex) {
                    delay(500)
                    isFirstPartLoad = true
                }
            }
            secondScope.launch {
                repo.addEvent(it)
                if(index == testList.lastIndex) {
                    delay(500)
                    isSecondPartLoad = true
                }
            }
        }
        while (!isFirstPartLoad&&!isSecondPartLoad) { delay(100) }
        val listFromDB = repo.getAllEvents().first()

        Assert.assertEquals(testList.size*2,listFromDB.size)
    }
    @Test
    fun test() = runBlocking {
        val testList = getTestEventList()
        val testScope = CoroutineScope(Job() + Dispatchers.IO)
        var isFinished = false
        testList.forEachIndexed { index, deviceEvent ->
            testScope.launch {
                repo.addEvent(deviceEvent)
                if(testList.lastIndex == index) isFinished = true
            }
        }
        while (!isFinished) { delay(100) }

        val listFromDb = repo.getAllEvents().first()

        Assert.assertEquals(testList,listFromDb)
    }

    private fun getTestEventList() : List<DeviceEvent> {
        return listOf(
            DeviceEvent.AttemptUnlockDevice.Failed(1,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Succeeded(2,System.currentTimeMillis()),
            DeviceEvent.AppOpen(3,null, packageName = "fgehfg",null,System.currentTimeMillis()),
            DeviceEvent.AppOpen(4,null, packageName = "fgehfg",null,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Failed(5,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Failed(6,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Succeeded(7,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Succeeded(8,System.currentTimeMillis()),
            DeviceEvent.AppOpen(9,"sfdsfsf", packageName = "fgehfg",null,System.currentTimeMillis()),
            DeviceEvent.AppOpen(10,"sfdsfsf", packageName = "34fgehfg",null,System.currentTimeMillis()),
            DeviceEvent.AppOpen(11,"sfdsfsf", packageName = "fgehfg",null,System.currentTimeMillis()),
            DeviceEvent.AttemptUnlockDevice.Succeeded(12,System.currentTimeMillis()),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    class MainDispatcherRule constructor(
        private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    ) : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }
}