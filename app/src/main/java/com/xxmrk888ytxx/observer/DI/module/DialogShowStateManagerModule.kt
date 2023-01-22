package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.DialogShowStateManager
import com.xxmrk888ytxx.observer.domain.DialogShowStateManager.DialogShowStateManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface DialogShowStateManagerModule {
    @Binds
    fun bindsDialogShowStateManager(
        dialogShowStateManagerImpl: DialogShowStateManagerImpl
    ) : DialogShowStateManager
}