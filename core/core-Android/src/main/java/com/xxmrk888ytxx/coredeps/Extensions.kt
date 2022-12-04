package com.xxmrk888ytxx.coredeps

import android.content.Context
import android.content.pm.PackageManager
import java.util.*

@Suppress("DEPRECATION")
fun Context.getApplicationNameByPackageName(packageName:String) : String? {
    try {
        val applicationInfo = this.packageManager.getApplicationInfo(packageName, 0)
        return this.packageManager.getApplicationLabel(applicationInfo).toString()
    }catch (e: PackageManager.NameNotFoundException) {
        return null
    }
}

fun Long.toTimeString() : String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val hours = calendar.get(Calendar.HOUR)
    val hoursString = if(hours < 10) "0$hours" else hours.toString()

    val minute = calendar.get(Calendar.MINUTE)
    val minuteString = if(minute < 10) "0$minute" else minute.toString()

    val second = calendar.get(Calendar.SECOND)
    val secondString = if(second < 10) "0$second" else second.toString()

    return "$hoursString:$minuteString:$secondString"
}