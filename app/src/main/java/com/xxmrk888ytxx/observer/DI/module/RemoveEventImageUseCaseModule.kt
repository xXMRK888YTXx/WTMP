package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.RemoveEventImageUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.RemoveEventImageUseCase.RemoveEventImageUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface RemoveEventImageUseCaseModule {
    @Binds
    fun bindRemoveEventImageUseCase(
        removeEventImageUseCaseImpl: RemoveEventImageUseCaseImpl
    ) : RemoveEventImageUseCase
}