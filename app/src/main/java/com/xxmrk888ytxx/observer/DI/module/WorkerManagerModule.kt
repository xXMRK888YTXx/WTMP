package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.PeriodicWorkWorkerManager
import com.xxmrk888ytxx.coredeps.SharedInterfaces.SingleWorkWorkerManager
import com.xxmrk888ytxx.workers.PeriodicWorkWorkerManagerImpl
import com.xxmrk888ytxx.workers.SingleWorkWorkerManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface WorkerManagerModule {
    @Binds
    fun bindSingleWorkWorkerManagerImpl(singleWorkWorkerManagerImpl: SingleWorkWorkerManagerImpl): SingleWorkWorkerManager

    @Binds
    fun bindPeriodicWorkWorkerManager(periodicWorkWorkerManager: PeriodicWorkWorkerManagerImpl): PeriodicWorkWorkerManager
}