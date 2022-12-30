package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
import com.xxmrk888ytxx.observer.domain.PermissionsManager.PermissionsManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface PermissionsManagerModule {
    @Binds
    fun bindPermissionsManager(permissionsManagerImpl: PermissionsManagerImpl) : PermissionsManager
}