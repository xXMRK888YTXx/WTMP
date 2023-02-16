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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppOpenTimeLimitRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDataBase

    private val context by lazy {
        ApplicationProvider.getApplicationContext<Context>()
    }

    private val repo = spyk(AppOpenTimeLimitRepositoryImpl(context))

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()

        val dataBaseComponent = object : DataBaseComponent {
            override val deviceEventDao: DeviceEventDao
                get() = TODO("Not yet implemented")
            override val appOpenEventDao: AppOpenEventDao
                get() = TODO("Not yet implemented")
            override val unlockDeviceEvent: UnlockDeviceEvent
                get() = TODO("Not yet implemented")
            override val trackedAppDao: TrackedAppDao
                get() = TODO("Not yet implemented")
            override val appOpenTimeLimitDao: AppOpenTimeLimitDao
                get() = database.getAppOpenTimeLimitDao()


        }

        mockkStatic(DaggerDataBaseComponent::class)
        every { DaggerDataBaseComponent.factory().create(context) } returns dataBaseComponent
    }

    @After
    fun close() {
        database.close()
    }

    @Test
    fun insertLimitInOtherCoroutinesExpectAllLimitAddInDB() = runBlocking {
        val testList = getTestList()

        testList.forEach {
            GlobalScope.launch {
                repo.addLimit(it.first,it.second)
            }
        }

        delay(300)
        repo.addLimit("e",325L)

        testList.forEach {
            Assert.assertEquals(it.second,repo.getTimeLimitForApp(it.first)!!.second)
        }
    }

    @Test
    fun addLimitAndReplaceTimeExpectInDBSSaveLasted() = runTest {
        val testData = Pair("1",1L)
        val testData2 = Pair("1",2L)
        repo.addLimit(testData.first,testData.second)
        Assert.assertEquals(testData,repo.getTimeLimitForApp(testData.first))

        repo.addLimit(testData2.first,testData2.second)

        Assert.assertEquals(testData2,repo.getTimeLimitForApp(testData.first))
    }

    @Test
    fun insertLimitAndRemoveThemExpectAfterRemoveReturnNull() = runTest {
        val testData = Pair("1",1L)

        repo.addLimit(testData.first,testData.second)
        Assert.assertEquals(testData,repo.getTimeLimitForApp(testData.first))
        repo.removeLimit(testData.first)

        Assert.assertNull(repo.getTimeLimitForApp(testData.first))
    }


    private fun getTestList() : List<Pair<String,Long>> {
        val list = mutableListOf<Pair<String,Long>>()
        repeat(10) {
            list.add(Pair(it.toString(),System.currentTimeMillis()))
        }

        return list
    }
}