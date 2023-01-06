package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState

/**
 * [Ru]
 * Данный интерфейс устававливает состояние приложение.
 * Если состояние является false, то должны быть отключены всё отслежевания
 */
/**
 * [En]
 * This interface sets the state of the application.
 * If the state is false, then all tracking should be disabled
 */
interface AppStateChanger {
    suspend fun updateAppState(state:Boolean)
}