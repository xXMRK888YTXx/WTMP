package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.SucceededUnlockTrackedConfig

import com.xxmrk888ytxx.coredeps.models.SucceededUnlockTrackedConfig
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Данный интерфейс используется для предоставления хранязегося в памяти приложения
 * [SucceededUnlockTrackedConfig]
 */
/**
 * [En]
 * This interface is used to provide in-memory application
 * [SucceededUnlockTrackedConfig]
 */
interface SucceededUnlockTrackedConfigProvider {
    val config: Flow<SucceededUnlockTrackedConfig>
}