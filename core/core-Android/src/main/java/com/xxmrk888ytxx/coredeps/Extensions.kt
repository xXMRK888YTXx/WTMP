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

fun Long.toDateString(context: Context) : String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dayString = if(day < 10) "0$day" else day.toString()

    val month = calendar.get(Calendar.MONTH)
    val monthString = monthToString(month,context)

    val year = calendar.get(Calendar.YEAR)
    val yearString = year.toString()

    return "$dayString $monthString $yearString"
}

internal fun monthToString(month:Int,context: Context) : String {
    context.resources.apply {
        return when(month) {
            0 -> getString(R.string.January)
            1 -> getString(R.string.February)
            2 -> getString(R.string.March)
            3 -> getString(R.string.April)
            4 -> getString(R.string.May)
            5 -> getString(R.string.June)
            6 -> getString(R.string.July)
            7 -> getString(R.string.August)
            8 -> getString(R.string.September)
            9 -> getString(R.string.October)
            10 -> getString(R.string.November)
            11 -> getString(R.string.December)
            else -> return ""
        }
    }
}