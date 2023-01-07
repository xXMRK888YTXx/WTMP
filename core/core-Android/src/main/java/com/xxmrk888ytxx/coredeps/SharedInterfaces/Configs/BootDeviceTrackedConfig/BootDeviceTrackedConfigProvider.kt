package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig

import com.xxmrk888ytxx.coredeps.models.BootDeviceTrackedConfig
import kotlinx.coroutines.flow.Flow

interface BootDeviceTrackedConfigProvider {
    val config:Flow<BootDeviceTrackedConfig>
}