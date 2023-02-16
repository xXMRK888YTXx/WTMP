package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import com.xxmrk888ytxx.coredeps.models.AppOpenTimeLimitModel

interface AppOpenTimeLimitRepository {

    suspend fun getTimeLimitForApp(packageName:String) : AppOpenTimeLimitModel?


    suspend fun addLimit(appOpenTimeLimitModel:AppOpenTimeLimitModel)


    suspend fun removeLimit(packageName: String)
}