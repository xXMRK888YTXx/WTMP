package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.ResourcesProvider
import com.xxmrk888ytxx.observer.domain.ResourcesProvider.ResourcesProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface ResourcesProviderModule {

    @Binds
    fun bindResourcesProvider(resourcesProvider: ResourcesProviderImpl) : ResourcesProvider
}