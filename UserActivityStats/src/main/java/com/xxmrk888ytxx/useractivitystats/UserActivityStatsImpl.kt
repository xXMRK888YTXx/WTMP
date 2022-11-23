package com.xxmrk888ytxx.useractivitystats

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.xxmrk888ytxx.androidextension.IntentExtension.sendIntent
import com.xxmrk888ytxx.coredeps.Interfaces.Repository.UserActivityStats
import javax.inject.Inject

/**
 * [Ru] реализация для [UserActivityStats]
 * [En] Implementation for the [UserActivityStats] interface
 */
class UserActivityStatsImpl @Inject constructor(private val context: Context)
    : UserActivityStats {

    private val userStatsManager: UsageStatsManager by lazy {
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    override fun requestPackageUsageStatePermission() {
        sendIntent(context,Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)){}
    }

    override fun getUsingHistory(beginTime:Long,endTime:Long): Map<String, UsageStats> {
        return userStatsManager.queryAndAggregateUsageStats(beginTime,endTime) ?: emptyMap()
    }

    override fun getUsingHistoryByPackageName(
        packageName:String,
        beginTime: Long,
        endTime: Long) = getUsingHistory(beginTime, endTime)[packageName]

}