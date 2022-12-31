package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateProvider
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