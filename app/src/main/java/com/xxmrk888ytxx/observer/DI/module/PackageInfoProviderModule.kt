package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.packageinfoprovider.PackageInfoProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface PackageInfoProviderModule {

    @Binds
    fun bindPackageInfoProvider(packageInfoProviderImpl: PackageInfoProviderImpl) : PackageInfoProvider
}