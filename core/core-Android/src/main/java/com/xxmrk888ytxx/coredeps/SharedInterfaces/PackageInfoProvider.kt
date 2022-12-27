package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.graphics.drawable.Drawable
import com.xxmrk888ytxx.coredeps.models.AppInfo

/**
 * [Ru]
 * Класс реализующий данный интерфейс должен предоставлять информацию об установленных приложенях
 */

/**
 * [En]
 * The class that implements this interface must provide information about installed applications
 */
interface PackageInfoProvider {

    fun getAppName(packageName:String) : String?

    fun getAppIcon(packageName:String) : Drawable?

    suspend fun getAllApplicationInfo() : List<AppInfo>

}