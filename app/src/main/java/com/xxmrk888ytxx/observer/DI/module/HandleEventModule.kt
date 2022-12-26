package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.HandleEventUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface HandleEventModule {
    @Binds
    fun bindHandleEvent(handleEventUseCaseImpl: HandleEventUseCaseImpl) : HandleEventUseCase
}