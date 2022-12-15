package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.database.DeviceEventRepositoryImpl
import com.xxmrk888ytxx.observer.DI.AppScope
import dagger.Binds
import dagger.Module

@Module
interface DatabaseModule {
    @Binds
    @AppScope
    fun bindDeviceEventRepository(
        deviceEventRepositoryImpl: DeviceEventRepositoryImpl
    ) : DeviceEventRepository
}