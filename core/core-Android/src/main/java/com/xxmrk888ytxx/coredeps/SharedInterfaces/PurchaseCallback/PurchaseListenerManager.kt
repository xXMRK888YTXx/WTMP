package com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback

/**
 * [Ru]
 * Интерфейс для управления [PurchaseListener]
 */
/**
 * [En]
 * * Interface for managing [PurchaseListener]
 */
interface PurchaseListenerManager {

    fun registerListener(purchaseListener: PurchaseListener)

    fun unregisterListener(purchaseListener: PurchaseListener)

    fun notifyListeners()
}