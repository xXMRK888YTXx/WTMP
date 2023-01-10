package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.RemoveAppManager
import com.xxmrk888ytxx.observer.domain.RemoveAppManager.RemoveAppManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface RemoveAppManagerModule {
    @Binds
    fun bindRemoveAppManager(removeAppManager: RemoveAppManagerImpl) : RemoveAppManager
}