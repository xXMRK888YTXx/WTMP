package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.WorkDayConfig

import com.xxmrk888ytxx.coredeps.models.WorkTimeConfig
import kotlinx.coroutines.flow.Flow

interface WorkTimeConfigProvider {
    val workConfigFlow:Flow<WorkTimeConfig>
}