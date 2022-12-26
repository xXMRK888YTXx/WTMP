package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback.EventDeviceTrackerCallbackImpl
import dagger.Binds
import dagger.Module

@Module
internal interface AppOpenChangedCallbackModule {
    @Binds
    fun bindAppOpenChangedCallback(
        appOpenTrackerCallback: EventDeviceTrackerCallbackImpl
    ) : EventDeviceTrackerCallback
}