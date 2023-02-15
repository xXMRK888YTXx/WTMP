package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.FailedUnlockLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.FailedUnlockTimeOperationLimitManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface TimeLimitManagersModule {
    @Binds
    @FailedUnlockLimitManagerQualifier
    fun bindTimeLimitManagerForFailedUnlock(
        failedUnlockTimeOperationLimitManagerImpl: FailedUnlockTimeOperationLimitManagerImpl
    ) : TimeOperationLimitManager<Nothing>
}