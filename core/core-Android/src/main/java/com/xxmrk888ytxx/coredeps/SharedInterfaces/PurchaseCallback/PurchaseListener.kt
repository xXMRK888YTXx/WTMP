package com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback

/**
 * [Ru]
 * Интерфейс слушатель, при совершении любой покупки в приложении
 * [PurchaseListenerManager] должен оповещать об этом по средствам вызова
 * метода [onPurchase]
 */
/**
 * [En]
 * Listener interface, when making any in-app purchase
 * [PurchaseListenerManager] should notify about this by means of a call
 * * of the [onPurchase] method
 */
interface PurchaseListener {
    fun onPurchase()
}