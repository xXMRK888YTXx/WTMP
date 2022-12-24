package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.observer.domain.ImageRepository.ImageRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface ImageRepositoryModule {

    @Binds
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl) : ImageRepository

}