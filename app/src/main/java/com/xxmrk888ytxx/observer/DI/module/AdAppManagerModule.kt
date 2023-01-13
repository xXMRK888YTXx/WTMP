package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.adutils.AdAppManager
import com.xxmrk888ytxx.adutils.AdMobManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface AdAppManagerModule {
    @Binds
    fun bindAdAppManager(adMobManagerImpl: AdMobManagerImpl) : AdAppManager
}