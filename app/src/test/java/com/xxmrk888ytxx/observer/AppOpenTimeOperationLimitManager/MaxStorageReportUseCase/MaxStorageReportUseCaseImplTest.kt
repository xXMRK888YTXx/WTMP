package com.xxmrk888ytxx.observer.AppOpenTimeOperationLimitManager.MaxStorageReportUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.models.StorageConfig
import com.xxmrk888ytxx.observer.domain.UseCase.MaxStorageEventUseCase.MaxStorageReportUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.testng.Assert

class MaxStorageReportUseCaseImplTest {

    private val configProvider = mockk<StorageConfigProvider>()

    private val repo = mockk<DeviceEventRepository>()

    private val useCase: MaxStorageReportUseCase by lazy {
        MaxStorageReportUseCaseImpl(configProvider)
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
        val expectedList = list.sortedBy { it.time }.drop(10)
        every { configProvider.storageConfig } returns flowOf(StorageConfig(5))
        every { repo.getAllEvents() } returns flowOf(list)
        coEvery { repo.removeEvent(any()) } answers {
            val id = this.args.get(0) as Int
            list.removeIf { it.eventId == id }
        }

        useCase.execute(repo)

        coVerify { repo.removeEvent(any()) }
        Assert.assertEquals(list.sortedBy { it.eventId },expectedList.sortedBy { it.eventId })
    }

    @Test
    fun inConfigMaxCountMoreInListExpectUseCaseNotRemoveReports() = runBlocking {
        val list = getTestEventList()
        every { configProvider.storageConfig } returns flowOf(StorageConfig(20))
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