package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.BillingManager
import com.xxmrk888ytxx.observer.DI.AppScope
import com.xxmrk888ytxx.observer.domain.BillingManager.BillingManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface BillingManagerModule {
    @Binds
    @AppScope
    fun bindBillingManager(billingManagerImpl: BillingManagerImpl) : BillingManager
}