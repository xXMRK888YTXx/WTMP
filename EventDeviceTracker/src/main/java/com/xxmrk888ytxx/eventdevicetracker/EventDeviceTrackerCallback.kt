package com.xxmrk888ytxx.eventdevicetracker

/**
 * [Ru]
 * Интерфейс для получение событий устройства таких как:
 * - Смена текущего открытого приложения
 * - разблокировка устройства
 */
/**
 * [En]
 * Interface for receiving device events such as:
 * - Change the current open application
 * - unlock device
 */
interface EventDeviceTrackerCallback {
    /**
     * [Ru]
     * Дополнительный параметры для работы трекера
     */
    /**
     * [En]
     * Additional parameters for tracker operation
     */
    val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().build()

    /**
     * [Ru]
     * Метод который должен вызываться при смене текущего открытого приложения
     */
    /**
     * [En]
     * Method to be called when changing the currently open application
     */
    fun onOpenAppChanged(packageName:String)

    /**
     * [Ru]
     * Метод который должен вызываться при разблокировке устройства
     */
    /**
     * [En]
     * Method to be called when unlocking the device
     */
    fun onScreenOn()

    fun onServiceDestroy()
}