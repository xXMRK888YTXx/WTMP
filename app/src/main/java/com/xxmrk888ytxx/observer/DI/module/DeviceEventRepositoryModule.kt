package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.AppOpenTimeLimitRepository
import com.xxmrk888ytxx.database.AppOpenTimeLimitRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DeviceEventRepositoryModule {
    @Binds
    fun bindAppOpenTimeLimitRepository(
        appOpenTimeLimitRepositoryImpl: AppOpenTimeLimitRepositoryImpl
    ) : AppOpenTimeLimitRepository
}