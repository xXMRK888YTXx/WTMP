package com.xxmrk888ytxx.coredeps

import android.content.Context
import android.content.pm.PackageManager

@Suppress("DEPRECATION")
fun Context.getApplicationNameByPackageName(packageName:String) : String? {
    try {
        val applicationInfo = this.packageManager.getApplicationInfo(packageName, 0)
        return this.packageManager.getApplicationLabel(applicationInfo).toString()
    }catch (e: PackageManager.NameNotFoundException) {
        return null
    }
}

fun Context.getApplicationIconByPackageName(packageName:String) =
    this.packageManager.getApplicationIcon(packageName)
