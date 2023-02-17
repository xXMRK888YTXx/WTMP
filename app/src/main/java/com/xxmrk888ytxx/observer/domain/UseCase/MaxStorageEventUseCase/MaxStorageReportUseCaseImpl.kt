package com.xxmrk888ytxx.observer.domain.UseCase.MaxStorageEventUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.StorageConfig.StorageConfigProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MaxStorageReportUseCaseImpl @Inject constructor(
    private val configProvider: StorageConfigProvider
) : MaxStorageReportUseCase {
    override suspend fun execute(deviceEventRepository: DeviceEventRepository) {
        withContext(Dispatchers.IO) {
            val maxStorageEvent = configProvider.storageConfig.first().maxReportCount
            if(maxStorageEvent == 0) return@withContext

            val eventList = deviceEventRepository.getAllEvents().first()

            if(eventList.size <= maxStorageEvent) return@withContext

            val willBeRemoveReport = eventList.sortedBy { it.time }
                .dropLast(eventList.size - (eventList.size - maxStorageEvent))

            willBeRemoveReport.forEach {
                deviceEventRepository.removeEvent(it.eventId)
            }
        }
    }
}