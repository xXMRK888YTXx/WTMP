package com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager

interface TimeOperationLimitManager<D> {
    suspend fun enableLimit(installedPeriodLimit:Int, otherData:D? = null)

    suspend fun isLimitEnable(installedPeriodLimit:Int,otherData: D? = null) : Boolean

    suspend fun disableLimit(otherData: D? = null)
}