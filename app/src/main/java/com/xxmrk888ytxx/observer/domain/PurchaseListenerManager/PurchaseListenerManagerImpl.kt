package com.xxmrk888ytxx.observer.domain.PurchaseListenerManager

import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListener
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListenerManager
import com.xxmrk888ytxx.observer.DI.AppScope
import javax.inject.Inject

@AppScope
class PurchaseListenerManagerImpl @Inject constructor() : PurchaseListenerManager {

    private val listeners = mutableSetOf<PurchaseListener>()

    override fun registerListener(purchaseListener: PurchaseListener) {
        listeners.add(purchaseListener)
    }

    override fun unregisterListener(purchaseListener: PurchaseListener) {
        listeners.remove(purchaseListener)
    }

    override fun notifyListeners() {
        listeners.forEach { it.onPurchase() }
    }
}