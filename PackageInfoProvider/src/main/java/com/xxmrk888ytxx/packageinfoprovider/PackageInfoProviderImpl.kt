package com.xxmrk888ytxx.packageinfoprovider

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.models.AppInfo
import javax.inject.Inject


/**
 * [Ru]
 * Данный класс является реализацией интерфейса [PackageInfoProvider]
 * Для работы этого класс необходимо на версии android 11+ необходимо
 * разрешение [android.permission.QUERY_ALL_PACKAGES] (уже прописано в манифесте модуля)
 */

/**
 * [En]
 * This class is an implementation of the [PackageInfoProvider] interface
 * For this class to work, it is necessary on android version 11+
 * permission [android.permission.QUERY_ALL_PACKAGES] (already set in the module manifest)
 */
class PackageInfoProviderImpl @Inject constructor(
    private val context: Context,
) : PackageInfoProvider {

    /**
     * [Ru]
     *  Данная функция расширения предназначена для получения названия приложение
     *  по его имени пакета.
     */
    /**
     *  [En]
     *  This extension function is assigned to get the name of the application
     * by its package name.
     *
     */

    @Suppress("DEPRECATION")
    override fun getAppName(packageName: String): String? {
        return try {
            val applicationInfo = context.packageManager.getApplicationInfo(packageName, 0)

            context.packageManager.getApplicationLabel(applicationInfo).toString()
        }catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    /**
     * [Ru]
     *  Данная функция расширения предназначена для получения иконки приложение
     *  по его имени пакета.
     */
    /**
     *  [En]
     *  This extension function is designed to get the application icon
     * by its package name.
     */

    override fun getAppIcon(packageName: String): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(packageName)
        }catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    @Suppress("DEPRECATION")
    override suspend fun getAllApplicationInfo(): List<AppInfo> {
        val packages: List<ApplicationInfo> =
            context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        return packages.filter { it.packageName != null }.map {
            AppInfo(
                appName = getAppName(it.packageName) ?: "error",
                appPackageName = it.packageName,
                appIcon = getAppIcon(it.packageName)?.toBitmap()
            )
        }.filter { it.appName != "error" }
    }

}