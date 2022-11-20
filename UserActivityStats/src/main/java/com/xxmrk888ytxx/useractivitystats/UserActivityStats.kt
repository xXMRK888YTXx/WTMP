package com.xxmrk888ytxx.useractivitystats

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.xxmrk888ytxx.androidextension.IntentExtension.sendIntent

class UserActivityStats(private val context: Context) {

    private val userStatsManager: UsageStatsManager by lazy {
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    fun requestPackageUsageStatePermission() {
        sendIntent(context,Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)){}
    }

    fun getUsingHistory(beginTime:Long,endTime:Long = System.currentTimeMillis()): Map<String, UsageStats> {
        return userStatsManager.queryAndAggregateUsageStats(beginTime,endTime) ?: emptyMap()
    }

    fun getUsingHistoryByPackageName(
        packageName:String,
        beginTime: Long,
        endTime: Long
        = System.currentTimeMillis()) = getUsingHistory(beginTime, endTime)[packageName]

}