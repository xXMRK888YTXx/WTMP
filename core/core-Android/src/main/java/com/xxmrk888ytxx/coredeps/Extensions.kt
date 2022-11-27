package com.xxmrk888ytxx.coredeps

import android.content.Context
import android.content.pm.PackageManager

@Suppress("DEPRECATION")
/**
*   [Ru]
 *  Данная функция расширения предназначена для получения названия приложение
 *  по его имени пакета.
 *  Для android 11+ необходимо разрешение android.permission.QUERY_ALL_PACKAGES
 *  [En]
 *  This extension function is assigned to get the name of the application
 * by its package name.
 * For android 11+, android.permission.QUERY_ALL_PACKAGES permission is required
 */
fun Context.getApplicationNameByPackageName(packageName:String) : String? {
    try {
        val applicationInfo = this.packageManager.getApplicationInfo(packageName, 0)
        return this.packageManager.getApplicationLabel(applicationInfo).toString()
    }catch (e: PackageManager.NameNotFoundException) {
        return null
    }
}
/**
 *   [Ru]
 *  Данная функция расширения предназначена для получения иконки приложение
 *  по его имени пакета.
 *  Для android 11+ необходимо разрешение android.permission.QUERY_ALL_PACKAGES
 *  [En]
 *  This extension function is designed to get the application icon
 * by its package name.
 * For android 11+, android.permission.QUERY_ALL_PACKAGES permission is required
 */
fun Context.getApplicationIconByPackageName(packageName:String) =
    this.packageManager.getApplicationIcon(packageName)
