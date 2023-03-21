package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.IsNowWorkTimeCheckUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.IsNowWorkTimeCheckUseCase.IsNowWorkTimeCheckUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface IsNowWorkTimeCheckUseCaseModule {
    @Binds
    fun bindIsNowWorkTimeCheckUseCase(
        IsNowWorkTimeCheckUseCase:IsNowWorkTimeCheckUseCaseImpl
    ) : IsNowWorkTimeCheckUseCase
}