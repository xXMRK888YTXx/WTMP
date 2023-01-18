package com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback

interface PurchaseListenerManager {

    fun registerListener(purchaseListener: PurchaseListener)

    fun unregisterListener(purchaseListener: PurchaseListener)

    fun notifyListeners()
}