package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TrackedAppRepository
import com.xxmrk888ytxx.database.TrackedAppRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface TrackedAppRepositoryModule {
    @Binds
    fun bindTrackedAppRepository(
        trackedAppRepositoryImpl: TrackedAppRepositoryImpl
    ) : TrackedAppRepository
}