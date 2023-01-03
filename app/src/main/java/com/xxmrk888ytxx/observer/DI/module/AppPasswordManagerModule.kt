package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword.AppPasswordProvider
import com.xxmrk888ytxx.observer.domain.AppPasswordManager.AppPasswordManager
import dagger.Binds
import dagger.Module

@Module
internal interface AppPasswordManagerModule {

    @Binds
    fun bindAppPasswordProvider(appPasswordManager: AppPasswordManager) : AppPasswordProvider

    @Binds
    fun bindAppPasswordChanger(appPasswordManager: AppPasswordManager) : AppPasswordChanger
}