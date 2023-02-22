package com.xxmrk888ytxx.observer.AppOpenTimeOperationLimitManager.MaxTimeStorageReportUseCaseImplTest

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.models.StorageConfig
import com.xxmrk888ytxx.observer.domain.UseCase.MaxTimeStorageReportUseCase.MaxTimeStorageReportUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.testng.Assert
import java.util.*

class MaxTimeStorageReportUseCaseImplTest {
    private val configProvider = mockk<StorageConfigProvider>()

    private val repo = mockk<DeviceEventRepository>()

    private val useCase: MaxTimeStorageReportUseCase by lazy {
        MaxTimeStorageReportUseCaseImpl(configProvider)
    }

    @Test
    fun inConfigSetMaxReportIsZeroExpectUseCaseWillNotWork() = runBlocking {
        val list = getTestEventList()
        every { configProvider.storageConfig } returns flowOf(StorageConfig())

        useCase.execute(repo)

        coVerify(exactly = 0) { repo.removeEvent(any()) }
    }

    @Test
    fun callUseCaseExpectInTestListSizeMustBeEqualsSizeInConfig() = runBlocking {
        val list = getTestEventList().toMutableList().asReversed()
        val expectedList = list.sortedBy { it.time }.drop(5)
        val calendar = mockk<Calendar>()
        every { calendar.timeInMillis } returns 15
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { configProvider.storageConfig } returns flowOf(StorageConfig(maxReportStorageTime = 10))
        every { repo.getAllEvents() } returns flowOf(list)
        coEvery { repo.removeEvent(any()) } answers {
            val id = this.args.get(0) as Int
            list.removeIf { it.eventId == id }
        }

        useCase.execute(repo)


        println(list.map { it.time })
        println(expectedList.map { it.time })
        coVerify { repo.removeEvent(any()) }
        Assert.assertEquals(list.sortedBy { it.eventId },expectedList.sortedBy { it.eventId })
    }

    @Test
    fun inConfigMaxCountMoreInListExpectUseCaseNotRemoveReports() = runBlocking {
        val list = getTestEventList()
        val calendar = mockk<Calendar>()
        every { calendar.timeInMillis } returns 15
        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
        every { configProvider.storageConfig } returns flowOf(StorageConfig(maxReportStorageTime = 20))
        every { repo.getAllEvents() } returns flowOf(list)

        useCase.execute(repo)

        coVerify(exactly = 0) { repo.removeEvent(any()) }
    }


    private fun getTestEventList(): List<DeviceEvent> {
        val list = mutableListOf<DeviceEvent>()

        (1..15).forEach {
            list.add(DeviceEvent.DeviceLaunch(it,it.toLong()))
        }

        return list
    }
}