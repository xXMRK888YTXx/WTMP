package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

interface AppOpenTimeLimitRepository {

    suspend fun getTimeLimitForApp(packageName:String) : Pair<String,Long>?


    suspend fun addLimit(packageName: String,time:Long)


    suspend fun removeLimit(packageName: String)
}