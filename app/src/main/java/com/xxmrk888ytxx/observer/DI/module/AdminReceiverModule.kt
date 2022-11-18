package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.adminreceiver.AdminEventsCallback
import com.xxmrk888ytxx.observer.AdminDeviceController.AdminDeviceController
import com.xxmrk888ytxx.observer.DI.AppScope
import dagger.Binds
import dagger.Module

@Module
internal interface AdminReceiverModule {
    @Binds
    @AppScope
    fun bindAdminEventCallback(adminDeviceController: AdminDeviceController) : AdminEventsCallback
}