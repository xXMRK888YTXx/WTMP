package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.NotificationAppManager
import com.xxmrk888ytxx.observer.domain.NotificationAppManager.NotificationAppManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface NotificationAppManagerModule {
    @Binds
    fun bindNotificationAppManager(
        notificationAppManager: NotificationAppManagerImpl
    ) : NotificationAppManager
}