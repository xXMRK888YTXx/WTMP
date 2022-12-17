package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.observer.domain.AppOpenTrackerCallbackImpl
import com.xxmrk888ytxx.openapptracker.AppOpenTrackerCallback
import dagger.Binds
import dagger.Module

@Module
interface AppOpenChangedCallbackModule {
    @Binds
    fun bindAppOpenChangedCallback(
        appOpenTrackerCallback: AppOpenTrackerCallbackImpl
    ) : AppOpenTrackerCallback
}