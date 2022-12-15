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
import kotlin.coroutines.EmptyCoroutineContext

@RunWith(AndroidJUnit4::class)
internal class DeviceEventRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDataBase

    private val context by lazy {
        ApplicationProvider.getApplicationContext<Context>()
    }

    private val repo = spyk(DeviceEventRepositoryImpl(context))

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()

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

        Assert.assertEquals(testList, listFromDB)
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

        Assert.assertEquals(expectedList, listFromDB)
    }

    @Test
    fun runTwoCoroutinesToAddEventsToDBEExpectTheAllHasBeenAdded() = runBlocking {
        val testList = getTestEventList()
        val firstScope = CoroutineScope(Job() + Dispatchers.IO)
        val secondScope = CoroutineScope(Job() + Dispatchers.IO)
        var isFirstPartLoad = false
        var isSecondPartLoad = false
        testList.forEachIndexed { index, it ->
            firstScope.launch {
                if (index == testList.lastIndex) {
                    delay(500)
                    repo.addEvent(it)
                    isFirstPartLoad = true
                } else repo.addEvent(it)
            }
            secondScope.launch {
                if (index == testList.lastIndex) {
                    delay(500)
                    repo.addEvent(it)
                    isSecondPartLoad = true
                } else repo.addEvent(it)
            }
        }
        while (!isFirstPartLoad || !isSecondPartLoad) {
            delay(100)
        }
        val listFromDB = repo.getAllEvents().first()

        Assert.assertEquals(testList.filter { it is DeviceEvent.AttemptUnlockDevice.Failed }.size * 2,
            listFromDB.filter { it is DeviceEvent.AttemptUnlockDevice.Failed }.size)
        Assert.assertEquals(testList.filter { it is DeviceEvent.AttemptUnlockDevice.Succeeded }.size * 2,
            listFromDB.filter { it is DeviceEvent.AttemptUnlockDevice.Succeeded }.size)
        Assert.assertEquals(testList.filter { it is DeviceEvent.AppOpen }.size * 2,
            listFromDB.filter { it is DeviceEvent.AppOpen }.size)
    }

    @Test
    fun addListToDBOOnDifferencesCoroutinesExpectReturnPrimaryList() = runBlocking {
        val testList = getTestEventList()
        val testScope = CoroutineScope(Job() + Dispatchers.IO)
        var isFinished = false
        testList.forEachIndexed { index, deviceEvent ->
            testScope.launch {
                if (testList.lastIndex == index) {
                    delay(500)
                    repo.addEvent(deviceEvent)
                    isFinished = true
                } else repo.addEvent(deviceEvent)
            }
        }
        while (!isFinished) {
            delay(100)
        }

        val listFromDb = repo.getAllEvents().first()

        Assert.assertEquals(testList.filter { it is DeviceEvent.AttemptUnlockDevice.Failed }.size,
            listFromDb.filter { it is DeviceEvent.AttemptUnlockDevice.Failed }.size)
        Assert.assertEquals(testList.filter { it is DeviceEvent.AttemptUnlockDevice.Succeeded }.size,
            listFromDb.filter { it is DeviceEvent.AttemptUnlockDevice.Succeeded }.size)
        Assert.assertEquals(testList.filter { it is DeviceEvent.AppOpen }.size,
            listFromDb.filter { it is DeviceEvent.AppOpen }.size)
    }

    @Test
    fun putListEventInDBAndStartThoCorutinesFirstAddNewItemsSecondRemovedExpectReturnsNewList() =
        runBlocking {
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            val scope2 = CoroutineScope(Job() + Dispatchers.IO)
            val testList = getTestEventList()
            testList.forEach { repo.addEvent(it) }
            var isFirstFinish = false
            var isSecondFinish = false
            testList.forEachIndexed { index, it ->
                scope.launch {
                    if (index == testList.lastIndex) {
                        delay(500)
                        repo.addEvent(it)
                        isFirstFinish = true
                    } else repo.addEvent(it)
                }
                scope2.launch {
                    if (index == testList.lastIndex) {
                        delay(500)
                        repo.removeEvent(it.eventId)
                        isSecondFinish = true
                    } else repo.removeEvent(it.eventId)
                }
            }
            while (!isFirstFinish || !isSecondFinish) {
                delay(100)
            }
            val expectedEventsId = testList.map { it.eventId + testList.size }
            val dbEventIds = repo.getAllEvents().first().map { it.eventId }

            Assert.assertEquals(expectedEventsId, dbEventIds)

        }

    @Test
    fun runTwoCoroutinesTheyNeedCollectAndRemoveNewItemAndAddItemsExpectReturnEmptyList() = runBlocking {
        val testScope = CoroutineScope(EmptyCoroutineContext)
        val testScope2 = CoroutineScope(EmptyCoroutineContext)
        val testScope3 = CoroutineScope(EmptyCoroutineContext)
        testScope.launch {
            repo.getAllEvents().collect {
                it.forEach {
                    repo.removeEvent(it.eventId)
                    println("FirstScope:I delete ${it.eventId}")
                }
            }
        }
        testScope2.launch {
            repo.getAllEvents().collect {
                it.forEach {
                    repo.removeEvent(it.eventId)
                    println("SecondScope:I delete ${it.eventId}")
                }
            }
        }
        testScope3.launch {
            getTestEventList().forEach {
                repo.addEvent(it)
                println("RunBlock:I add ${it.eventId}")
            }
        }
        delay(2000)

        Assert.assertEquals(0,repo.getAllEvents().first().size)
    }
}


private fun getTestEventList(): List<DeviceEvent> {
    return listOf(
        DeviceEvent.AttemptUnlockDevice.Failed(1, System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(2, System.currentTimeMillis()),
        DeviceEvent.AppOpen(3, null, packageName = "fgehfg", null, System.currentTimeMillis()),
        DeviceEvent.AppOpen(4, null, packageName = "fgehfg", null, System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Failed(5, System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Failed(6, System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(7, System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(8, System.currentTimeMillis()),
        DeviceEvent.AppOpen(9, "sfdsfsf", packageName = "fgehfg", null, System.currentTimeMillis()),
        DeviceEvent.AppOpen(10,
            "sfdsfsf",
            packageName = "34fgehfg",
            null,
            System.currentTimeMillis()),
        DeviceEvent.AppOpen(11,
            "sfdsfsf",
            packageName = "fgehfg",
            null,
            System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(12, System.currentTimeMillis()),
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
