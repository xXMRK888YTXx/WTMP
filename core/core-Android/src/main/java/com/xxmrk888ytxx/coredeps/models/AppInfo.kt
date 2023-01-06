package com.xxmrk888ytxx.coredeps.models

import android.graphics.Bitmap

/**
 * [Ru]
 * Класс предоставляет собой информацию о установленом приложении
 * @param appName - имя приложения
 * @param appPackageName - имя покета приложения
 * @param appIcon - икона приложения
 */
/**
 * [En]
 * The class represents information about the installed application
 * @param appName - application name
 * @param appPackageName - application package name
 * @param appIcon - application icon
 */
data class AppInfo(
    val appName:String,
    val appPackageName:String,
    val appIcon:Bitmap?
)