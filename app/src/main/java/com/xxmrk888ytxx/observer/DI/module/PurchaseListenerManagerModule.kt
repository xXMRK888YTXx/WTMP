package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.PurchaseCallback.PurchaseListenerManager
import com.xxmrk888ytxx.observer.DI.AppScope
import com.xxmrk888ytxx.observer.domain.PurchaseListenerManager.PurchaseListenerManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface PurchaseListenerManagerModule {
    @Binds
    @AppScope
    fun bindPurchaseListenerManager(
        purchaseListenerManager: PurchaseListenerManagerImpl
    ): PurchaseListenerManager
}