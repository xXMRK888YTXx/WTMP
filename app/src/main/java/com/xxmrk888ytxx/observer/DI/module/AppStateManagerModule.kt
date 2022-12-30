package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppStateProvider
import com.xxmrk888ytxx.observer.domain.AppStateManager.AppStateManager
import dagger.Binds
import dagger.Module

@Module
internal interface AppStateManagerModule {
    @Binds
    fun bindAppStateProvider(appStateManager: AppStateManager) : AppStateProvider

    @Binds
    fun bindAppStateChanger(appStateManager: AppStateManager) : AppStateChanger
}