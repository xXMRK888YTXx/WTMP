package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.CryptoManager
import com.xxmrk888ytxx.cryptomanager.CryptoManagerImpl
import dagger.Binds
import dagger.Module

@Module
interface CryptoManagerModule {
    @Binds
    fun bindCryptoManager(cryptoManager:CryptoManagerImpl) : CryptoManager
}