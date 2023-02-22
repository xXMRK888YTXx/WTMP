package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.AppOpenLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.FailedUnlockLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.SucceededUnlockLimitManagerQualifier
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.AppOpenTimeOperationLimitManagerImpl
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.FailedUnlockTimeOperationLimitManagerImpl
import com.xxmrk888ytxx.observer.domain.TimeLimitManagers.SucceededUnlockTimeOperationLimitManagerImpl
import dagger.Binds
import dagger.Module

@Module
internal interface TimeLimitManagersModule {
    @Binds
    @FailedUnlockLimitManagerQualifier
    fun bindTimeLimitManagerForFailedUnlock(
        failedUnlockTimeOperationLimitManagerImpl: FailedUnlockTimeOperationLimitManagerImpl
    ) : TimeOperationLimitManager<Nothing>

    @Binds
    @SucceededUnlockLimitManagerQualifier
    fun bindTimeLimitManagerForSucceededUnlock(
        succeededUnlockTimeOperationLimitManagerImpl: SucceededUnlockTimeOperationLimitManagerImpl
    ) : TimeOperationLimitManager<Nothing>

    @Binds
    @AppOpenLimitManagerQualifier
    fun bindTimeLimitManagerForAppOpen(
        appOpenTimeOperationLimitManagerImpl: AppOpenTimeOperationLimitManagerImpl
    ) : TimeOperationLimitManager<String>
}