package com.xxmrk888ytxx.coredeps.Interfaces.Repository

import android.app.usage.UsageStats

interface UserActivityStats {
    fun requestPackageUsageStatePermission()

    fun getUsingHistory(beginTime:Long,endTime:Long = System.currentTimeMillis()): Map<String, UsageStats>

    fun getUsingHistoryByPackageName(
        packageName:String,
        beginTime: Long,
        endTime: Long
        = System.currentTimeMillis()) : UsageStats?
}