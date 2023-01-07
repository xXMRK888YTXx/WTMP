package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.bootreceiver.BootCallback
import com.xxmrk888ytxx.observer.domain.BootCallback.BootCallbackImpl
import dagger.Binds
import dagger.Module

@Module
internal interface BootCallbackModule {

    @Binds
    fun bindBootCallback(bootCallbackImpl: BootCallbackImpl) : BootCallback

}