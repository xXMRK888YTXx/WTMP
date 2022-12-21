package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig

import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import kotlinx.coroutines.flow.Flow


interface SucceededUnlockTrackedConfigProvider {
    val config: Flow<SucceededUnlockTrackedConfig>
}