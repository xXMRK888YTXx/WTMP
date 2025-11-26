package com.xxmrk888ytxx.observer.DI.module

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.PackageNameAddedByUserRepository
import com.xxmrk888ytxx.database.PackageNameAddedByUserRepositoryImpl
import com.xxmrk888ytxx.observer.DI.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class PackageNameAddedByUserRepositoryModule {

    @Provides
    @AppScope
    fun bindPackageNameAddedByUserRepository(cotext: Context): PackageNameAddedByUserRepository =
        PackageNameAddedByUserRepositoryImpl(cotext)
}