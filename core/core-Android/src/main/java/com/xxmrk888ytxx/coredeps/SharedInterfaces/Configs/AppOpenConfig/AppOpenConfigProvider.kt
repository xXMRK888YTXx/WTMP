package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig

import com.xxmrk888ytxx.coredeps.models.AppOpenConfig
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Данный интерфейс используется для предоставления хранязегося в памяти приложения
 * [AppOpenConfig]
 */
/**
 * [En]
 * This interface is used to provide in-memory application
 * [AppOpenConfig]
 */
interface AppOpenConfigProvider {
    val config: Flow<AppOpenConfig>
}