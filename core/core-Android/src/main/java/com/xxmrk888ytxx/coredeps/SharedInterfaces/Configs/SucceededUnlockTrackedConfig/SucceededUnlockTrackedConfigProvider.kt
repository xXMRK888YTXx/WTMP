package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig

import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import kotlinx.coroutines.flow.Flow


interface SucceededUnlockTrackedConfigProvider {
    val config: Flow<FailedUnlockTrackedConfig>
}