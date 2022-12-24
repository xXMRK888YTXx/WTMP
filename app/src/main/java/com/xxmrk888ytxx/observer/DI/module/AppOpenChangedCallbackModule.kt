package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback.EventDeviceTrackerCallbackImpl
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import dagger.Binds
import dagger.Module

@Module
interface AppOpenChangedCallbackModule {
    @Binds
    fun bindAppOpenChangedCallback(
        appOpenTrackerCallback: EventDeviceTrackerCallbackImpl
    ) : EventDeviceTrackerCallback
}