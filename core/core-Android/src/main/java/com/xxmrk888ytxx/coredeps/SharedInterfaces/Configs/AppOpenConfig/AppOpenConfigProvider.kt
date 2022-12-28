package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig

import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import kotlinx.coroutines.flow.Flow

interface AppOpenConfigProvider {
    val config: Flow<AppOpenConfig>
}