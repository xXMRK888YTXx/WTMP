package com.xxmrk888ytxx.coredeps.Interfaces.Repository

import android.app.usage.UsageStats

/**
 * [Ru]
 * Данный интерфейс предназначен для получение статистики использования всех приложений или конкретного
 * за промежуток времени.
 * Для этого нужно запросить разрешения (android.permission.PACKAGE_USAGE_STATS), для этого необходимо реалозовать
 * метод [requestPackageUsageStatePermission]
 * [En]
 * This interface is designed to obtain statistics on the use of all applications or a specific
 * for a period of time.
 * To do this, you need to request permissions (android.permission.PACKAGE_USAGE_STATS), for this you need to implement
 * method [requestPackageUsageStatePermission]
 */

interface UserActivityStats {
    fun requestPackageUsageStatePermission()

    fun getUsingHistory(beginTime:Long,endTime:Long = System.currentTimeMillis()): Map<String, UsageStats>

    fun getUsingHistoryByPackageName(
        packageName:String,
        beginTime: Long,
        endTime: Long
        = System.currentTimeMillis()) : UsageStats?
}