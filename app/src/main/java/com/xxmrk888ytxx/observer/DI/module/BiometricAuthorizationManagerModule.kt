package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.BiometricAuthorizationManager
import com.xxmrk888ytxx.observer.domain.BiometricAuthorizationManager.BiometricAuthorizationManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface BiometricAuthorizationManagerModule {
    @Binds
    fun bindBiometricAuthorizationManager(
        biometricAuthorizationManager: BiometricAuthorizationManagerImpl
    ) : BiometricAuthorizationManager
}