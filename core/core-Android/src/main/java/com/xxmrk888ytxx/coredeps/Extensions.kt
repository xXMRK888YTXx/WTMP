package com.xxmrk888ytxx.coredeps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PermissionsManager
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
fun Long.toTimeString(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val hoursString = if (hours < 10) "0$hours" else hours.toString()

    val minute = calendar.get(Calendar.MINUTE)
    val minuteString = if (minute < 10) "0$minute" else minute.toString()

    val second = calendar.get(Calendar.SECOND)
    val secondString = if (second < 10) "0$second" else second.toString()

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
fun Long.toDateString(context: Context): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dayString = if (day < 10) "0$day" else day.toString()

    val month = calendar.get(Calendar.MONTH)
    val monthString = monthToString(month, context)

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
internal fun monthToString(month: Int, context: Context): String {
    context.resources.apply {
        return when (month) {
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

/**
 * [Ru]
 * Данная функция расширения, завершает всё запущенные ранее корутины
 * на текущем [CoroutineScope] и запускает новую корутину
 */
/**
 * [En]
 * This extension function terminates all previously launched coroutines
 * on the current [CoroutineScope] and starts a new coroutine
 */
fun CoroutineScope.launchAndCancelChildren(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    this.coroutineContext.cancelChildren()

    this.launch(context, start, block)
}

/**
 * [Ru]
 * Преобразует передавыемый класс в Json
 * У передаваемого класс должен быть сгенерирован Json Adapter
 * Для этого над классом необходимо поставить аннатацию [JsonClass]
 * и установить параметр [JsonClass.generateAdapter] = true
 */
/**
 * [En]
 * Converts the passed class to Json
 * The passed class must have a Json Adapter generated
 * To do this, the class must be annotated with [JsonClass]
 * and set parameter [JsonClass.generateAdapter] = true
 */
inline fun <reified JSONCLASS> toJson(model: JSONCLASS): String {
    val moshi = Moshi.Builder().build()
    return moshi.adapter(JSONCLASS::class.java).toJson(model)
}

/**
 * [Ru]
 * Преобразует Json строку в класс
 * Для успешного преобразования у класса который вы желаете получить
 * должен быть сгенерирован Json Adapter
 * Для этого над классом необходимо поставить аннатацию [JsonClass]
 * и установить параметр [JsonClass.generateAdapter] = true
 */
/**
 * [En]
 * Convert Json string to class
 * To successfully convert the class you wish to receive
 *must be generated Json Adapter
 * To do this, the class must be annotated with [JsonClass]
 * and set parameter [JsonClass.generateAdapter] = true
 */
inline fun <reified JSONCLASS> fromJson(
    jsonString: String,
    jsonClass: Class<JSONCLASS>
): JSONCLASS {
    val moshi = Moshi.Builder().build()
    return moshi.adapter(jsonClass).fromJson(jsonString) ?: throw JsonDataException()
}
/**
 * [Ru]
 * Преобразует Json строку в класс
 * Для успешного преобразования у класса который вы желаете получить
 * должен быть сгенерирован Json Adapter
 * Для этого над классом необходимо поставить аннатацию [JsonClass]
 * и установить параметр [JsonClass.generateAdapter] = true
 */
/**
 * [En]
 * Convert Json string to class
 * To successfully convert the class you wish to receive
 *must be generated Json Adapter
 * To do this, the class must be annotated with [JsonClass]
 * and set parameter [JsonClass.generateAdapter] = true
 */
@JvmName("fromJson1")
inline fun <reified JSONCLASS> fromJson(
    jsonString: String?,
    jsonClass: Class<JSONCLASS>
): JSONCLASS? {
    val moshi = Moshi.Builder().build()
    if (jsonString == null) return null
    return moshi.adapter(jsonClass).fromJson(jsonString)
}

/**
 * [Ru]
 * Функция отправляет сообщение в logcat в канал debug
 */
/**
 * [En]
 * The function sends a message to logcat in the debug channel
 */
fun logcatMessageD(text: String) {
    Log.d("MyLog", text)
}

inline fun <reified T : Any> T?.ifNotNull(onNotNull: T.() -> Unit) {
    if (this != null) {
        onNotNull(this)
    }
}

fun Context.sendOpenWebSiteIntent(url: String) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    } catch (e: Exception) {
        Log.e("MyLog", "Exception when try send ACTION_VIEW intent ${e.stackTraceToString()}")
    }
}

fun Context.sendCreateEmailIntent(email: String, chooserDescription: String) {
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(Intent.createChooser(emailIntent, chooserDescription))
    } catch (e: Exception) {
        Log.e("MyLog", "Exception when try send ACTION_SENDTO intent ${e.stackTraceToString()}")
    }
}

val PermissionsManager.isAllPermissionsGranted: Boolean
    get() = isCameraPermissionGranted() && isAccessibilityPermissionGranted() && isAdminPermissionGranted() && isNotificationPermissionGranted()