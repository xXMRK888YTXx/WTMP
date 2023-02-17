@file:OptIn(ExperimentalCoroutinesApi::class)

package com.xxmrk888ytxx.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.RemoveEventImageUseCase
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Dao.*
import io.mockk.*
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

    private val removeEventImageUseCase: RemoveEventImageUseCase = mockk(relaxed = true)

    private val maxStorageReportUseCase = mockk<MaxStorageReportUseCase>(relaxed = true)

    private val maxTimeStorageReportUseCase = mockk<MaxTimeStorageReportUseCase>(relaxed = true)

    private val repo = spyk(DeviceEventRepositoryImpl(context,removeEventImageUseCase,
        maxStorageReportUseCase, maxTimeStorageReportUseCase))

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()

        val dataBaseComponent = object : DataBaseComponent {
            override val deviceEventDao: DeviceEventDao = database.getDeviceEventDao()

            override val appOpenEventDao: AppOpenEventDao = database.getAppOpenEventDao()

            override val unlockDeviceEvent: UnlockDeviceEvent = database.getUnlockDeviceEventDao()
            override val trackedAppDao: TrackedAppDao
                get() = TODO("Not yet implemented")
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

    @Test
    fun addEventListInDBEExpectReturnsEqualsList() = runTest {
        val testList = getTestEventList()
        testList.forEach { repo.addEvent(it) }

        val listFromDB = repo.getAllEvents().first()

        Assert.assertEquals(testList.sortedByDescending { it.time }, listFromDB.sortedByDescending { it.time })
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

        Assert.assertEquals(expectedList.sortedByDescending { it.time }, listFromDB)
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

            Assert.assertEquals(expectedEventsId.sortedByDescending { it}, dbEventIds.sortedByDescending { it})

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

    @Test
    fun expectGetEventBetweenStartTimeAndEndTime() = runTest {
        val start = 4L
        val end = 8L
        val expectedList = getTestEventList().filter {
            it.time in start..end
        }.sortedByDescending { it.time }

        getTestEventList().forEach { repo.addEvent(it) }

        val listInDb = repo.getEventInTimeSpan(start,end).first()
        Assert.assertEquals(expectedList,listInDb)
    }

    @Test
    fun addEventExpectReturnsTheyId() = runBlocking {
        val testList = getTestEventList()
        val testList2 = getTestEventList()

        testList.forEach {
            val id = repo.addEvent(it)
            Assert.assertEquals(it.eventId,id)
        }
        testList2.forEach {
            val id = repo.addEvent(it)
            Assert.assertEquals(it.eventId+getTestEventList().size,id)
        }
    }

    @Test
    fun addEventInDBAAndGiveFirstAndLastEventsExpectReturnsSequelsEvents() = runBlocking {
        getTestEventList().forEach {
            repo.addEvent(it)
        }

        val itemFromDB = repo.getEvent(getTestEventList()[0].eventId)
        val itemFromDB2 = repo.getEvent(getTestEventList()[getTestEventList().lastIndex].eventId)

        Assert.assertEquals(getTestEventList()[0],itemFromDB.first())
        Assert.assertEquals(getTestEventList()[getTestEventList().lastIndex],itemFromDB2.first())
    }

    @Test
    fun addEventInDBAAndGetOneEventExpectReturnsEqualsEvent() = runBlocking {
        getTestEventList().forEach {
            repo.addEvent(it)
        }
        val scope = CoroutineScope(Job())
        CoroutineScope(Job()).launch {
            getTestEventList().forEach {
                scope.launch {
                    Assert.assertEquals(it,repo.getEvent(it.eventId).first())
                }
            }
            scope.cancel()
        }
        while (scope.isActive) { delay(100) }
    }

    @Test
    fun removeEventExpectRepositoryCallRemoveEventImageUseCase() = runTest {
        val testEvent = getTestEventList()[0]

        repo.addEvent(testEvent)
        repo.removeEvent(testEvent.eventId)

        coVerify(exactly = 1) { removeEventImageUseCase.execute(testEvent.eventId) }
    }

    @Test
    fun addEventAndRemoveItExpectRepositoryCallUseCasesForValidate() = runTest {
        val testEvent = getTestEventList()[0]

        repo.addEvent(testEvent)
        repo.removeEvent(testEvent.eventId)

        coVerify(exactly = 2) { maxStorageReportUseCase.execute(repo) }
        coVerify(exactly = 2) { maxTimeStorageReportUseCase.execute(repo) }
    }
}


private fun getTestEventList(): List<DeviceEvent> {
    return listOf(
        DeviceEvent.AttemptUnlockDevice.Failed(1, 1),
        DeviceEvent.AttemptUnlockDevice.Succeeded(2,2 ),
        DeviceEvent.AppOpen(3, null, packageName = "fgehfg", null, 3),
        DeviceEvent.AppOpen(4, null, packageName = "fgehfg", null, 4),
        DeviceEvent.AttemptUnlockDevice.Failed(5, 5),
        DeviceEvent.AttemptUnlockDevice.Failed(6, 6),
        DeviceEvent.AttemptUnlockDevice.Succeeded(7, 7),
        DeviceEvent.AttemptUnlockDevice.Succeeded(8, 8),
        DeviceEvent.AppOpen(9, null, packageName = "fgehfg", null, 9),
        DeviceEvent.DeviceLaunch(10,633464),
        DeviceEvent.AppOpen(11,
            null,
            packageName = "34fgehfg",
            null,
            10),
        DeviceEvent.AppOpen(12,
            null,
            packageName = "fgehfg",
            null,
            11),
        DeviceEvent.AttemptUnlockDevice.Succeeded(13, 12),
        DeviceEvent.DeviceLaunch(14,633464)
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
