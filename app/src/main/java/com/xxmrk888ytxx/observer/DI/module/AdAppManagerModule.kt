package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.adutils.AdAppManager
import com.xxmrk888ytxx.adutils.AdMobManagerImpl
import com.xxmrk888ytxx.adutils.AdStateManager
import com.xxmrk888ytxx.observer.domain.AdStateManager.AdStateManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface AdAppManagerModule {

    @Binds
    fun bindAdStateManager(adStateManagerImpl: AdStateManagerImpl) : AdStateManager

    @Binds
    fun bindAdAppManager(adMobManagerImpl: AdMobManagerImpl) : AdAppManager

}