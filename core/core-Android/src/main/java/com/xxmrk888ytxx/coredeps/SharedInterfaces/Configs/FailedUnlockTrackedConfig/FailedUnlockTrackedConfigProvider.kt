package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.FailedUnlockTrackedConfig

import com.xxmrk888ytxx.coredeps.models.FailedUnlockTrackedConfig
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Данный интерфейс используется для предоставления хранязегося в памяти приложения
 * [FailedUnlockTrackedConfig]
 */
/**
 * [En]
 * This interface is used to provide in-memory application
 * [FailedUnlockTrackedConfig]
 */
interface FailedUnlockTrackedConfigProvider {

    val config: Flow<FailedUnlockTrackedConfig>

}