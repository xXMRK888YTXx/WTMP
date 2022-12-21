package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig

import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import kotlinx.coroutines.flow.Flow


interface FailedUnlockTrackedConfigProvider {
    val config: Flow<FailedUnlockTrackedConfig>
}