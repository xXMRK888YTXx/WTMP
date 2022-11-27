package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.WorkerManager
import com.xxmrk888ytxx.workers.WorkerManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface WorkerManagerModule {
    @Binds
    fun bindWorkerManager(workerManager: WorkerManagerImpl) : WorkerManager
}