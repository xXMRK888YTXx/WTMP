package com.xxmrk888ytxx.observer.domain.UseCase.MaxTimeStorageReportUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

internal class MaxTimeStorageReportUseCaseImpl @Inject constructor(
    private val storageConfigProvider: StorageConfigProvider,
) : MaxTimeStorageReportUseCase {

    override suspend fun execute(deviceEventRepository: DeviceEventRepository) {
        withContext(Dispatchers.IO) {
            val maxStorageReportTime =
                storageConfigProvider.storageConfig.first().maxReportStorageTime
            if (maxStorageReportTime == 0L) return@withContext

            val eventList = deviceEventRepository.getAllEvents().first()

            val willBeRemoved =
                eventList.filter { it.time <= Calendar.getInstance().timeInMillis - maxStorageReportTime }

            willBeRemoved.forEach {
                deviceEventRepository.removeEvent(it.eventId)
            }
        }
    }
}