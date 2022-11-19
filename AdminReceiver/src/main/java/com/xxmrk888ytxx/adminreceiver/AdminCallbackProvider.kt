package com.xxmrk888ytxx.adminreceiver

/**
 * [Ru]
 * Данный интерфейс предназначен для предоставления [AdminEventsCallback], ресиверу [AdminReceiver],
 * для класс который наследуется [Application], должен реализовавыть этот интерфейс.
    [En]
This interface is intended to provide [AdminEventsCallback], for receiver [AdminReceiver],
 * for a class that inherits from [Application], must implement this interface.
 */
interface AdminCallbackProvider {
    val adminEventsCallback:AdminEventsCallback
}