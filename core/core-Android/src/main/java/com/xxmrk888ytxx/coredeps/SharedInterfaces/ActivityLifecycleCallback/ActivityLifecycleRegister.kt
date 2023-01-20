package com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback

/**
 * [Ru]
 * Данный интерфейс регистрирует [ActivityLifecycleRegister]
 */
/**
 * [En]
 * This interface registers [ActivityLifecycleRegister]
 */
interface ActivityLifecycleRegister {
    /**
     * [Ru]
     * Регистрирует callback, после регистрации должен быть
     * вызываться [ActivityLifecycleCallback.onRegister]
     */
    /**
     * [En]
     * Registers callback, after registration should be
     * called [ActivityLifecycleCallback.onRegister]
     */
    fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback)

    /**
     * [Ru]
     * Удаляет регестрацию для callback
     */
    /**
     * [En]
     * Удаляет регистрацию для callback
     */
    fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback)
}