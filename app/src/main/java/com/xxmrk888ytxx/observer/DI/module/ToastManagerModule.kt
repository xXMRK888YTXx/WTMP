package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.ToastManager
import com.xxmrk888ytxx.observer.domain.ToastManager.ToastManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ToastManagerModule {
    @Binds
    fun bindToastManager(toastManagerImpl: ToastManagerImpl) : ToastManager
}