package com.xxmrk888ytxx.coredeps

import android.content.Context
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * [Ru]
 * Данная функция расширения предназначена для конвертации числа с типом [Long]
 * в строку времени типа [XX:XX:XX]
 */

/**
 * [En]
 * This extension function is designed to convert a number with type [Long]
 * into a time string like [XX:XX:XX]
 */
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
/**
 * [Ru]
 * Данная функция расширения предназначена для конвертации числа с типом [Long]
 * в строку с датой типа [Day Month Year]
 */

/**
 * [En]
 * This extension function is designed to convert a number with type [Long]
 * to a date string like [Day Month Year]
 */
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

/**
 * [Ru]
 * Данная функция преобразует цифру месяца, в строку с названием месяца
 */

/**
 * [En]
 * This function converts the digit of the month into a string with the name of the month
 */
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

fun CoroutineScope.launchAndCancelChildren(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    this.coroutineContext.cancelChildren()

    this.launch(context, start, block)
}

inline fun <reified JSONCLASS> toJson(model:JSONCLASS) : String {
    val moshi = Moshi.Builder().build()
    return moshi.adapter(JSONCLASS::class.java).toJson(model)
}

inline fun <reified JSONCLASS> fromJson(jsonString: String,jsonClass:Class<JSONCLASS>) : JSONCLASS {
    val moshi = Moshi.Builder().build()
    return moshi.adapter(jsonClass).fromJson(jsonString) ?: throw JsonDataException()
}

@JvmName("fromJson1")
inline fun <reified JSONCLASS> fromJson(jsonString: String?, jsonClass:Class<JSONCLASS>) : JSONCLASS? {
    val moshi = Moshi.Builder().build()
    if(jsonString == null) return null
    return moshi.adapter(jsonClass).fromJson(jsonString)
}