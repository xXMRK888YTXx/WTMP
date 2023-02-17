package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.MaxTimeStorageReportUseCase
import com.xxmrk888ytxx.observer.domain.UseCase.MaxTimeStorageReportUseCase.MaxTimeStorageReportUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MaxTimeStorageReportUseCaseModule {
    @Binds
    fun bindMaxTimeStorageReportUseCase(
        maxTimeStorageReportUseCase: MaxTimeStorageReportUseCaseImpl
    ) : MaxTimeStorageReportUseCase
}