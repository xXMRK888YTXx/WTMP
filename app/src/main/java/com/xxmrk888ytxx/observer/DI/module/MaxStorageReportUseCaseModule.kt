package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxStorageReportUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.MaxStorageEventUseCase.MaxStorageReportUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MaxStorageReportUseCaseModule {
    @Binds
    fun bindMaxStorageReportUseCase(
        maxStorageReportUseCaseImpl: MaxStorageReportUseCaseImpl
    ) : MaxStorageReportUseCase
}