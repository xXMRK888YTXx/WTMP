package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.ImageProvider
import com.xxmrk888ytxx.observer.domain.ImageProvider.ImageProviderImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ImageProviderModule {
    @Binds
    fun bindImageProvider(imageProviderImpl: ImageProviderImpl) : ImageProvider
}