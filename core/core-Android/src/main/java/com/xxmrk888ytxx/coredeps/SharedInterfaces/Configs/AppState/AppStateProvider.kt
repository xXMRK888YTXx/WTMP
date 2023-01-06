package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState

import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Данный интерфейс предоставляет текущее состояние приложения
 */
/**
 * [En]
 * This interface provides the current state of the application
 */
interface AppStateProvider {
    val isAppEnable: Flow<Boolean>
}