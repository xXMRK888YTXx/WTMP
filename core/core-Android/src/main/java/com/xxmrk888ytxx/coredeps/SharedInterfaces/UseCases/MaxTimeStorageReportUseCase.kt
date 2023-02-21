package com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository

/**
 * [Ru]
 * UseCase для удаление устаревших событий
 */
/**
 * [En]
 * * Use Case to delete obsolete events
 */
interface MaxTimeStorageReportUseCase {
    suspend fun execute(deviceEventRepository: DeviceEventRepository)
}