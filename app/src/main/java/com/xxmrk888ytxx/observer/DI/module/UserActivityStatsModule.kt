package com.xxmrk888ytxx.observer.DI.module

import com.xxmrk888ytxx.coredeps.Interfaces.Repository.UserActivityStats
import com.xxmrk888ytxx.useractivitystats.UserActivityStatsImpl
import dagger.Binds
import dagger.Module

@Module
internal interface UserActivityStatsModule {
    @Binds
    fun bindUserActivityStats(userActivityStatsImpl: UserActivityStatsImpl) : UserActivityStats
}