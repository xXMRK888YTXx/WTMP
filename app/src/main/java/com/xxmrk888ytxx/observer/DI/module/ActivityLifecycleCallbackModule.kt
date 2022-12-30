package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback
import com.xxmrk888ytxx.mainscreen.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
internal interface ActivityLifecycleCallbackModule {
    @Binds
    @IntoSet
    fun bindMainViewModelActivityCallback(mainViewModel: MainViewModel) : ActivityLifecycleCallback
}