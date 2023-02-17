package com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository

interface MaxTimeStorageReportUseCase {
    suspend fun execute(deviceEventRepository: DeviceEventRepository)
}