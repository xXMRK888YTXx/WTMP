package com.xxmrk888ytxx.settingsscreen

import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType
import java.time.LocalTime

/**
 * [Ru]
 * Данная функция расшерения преданзначена для получения количества видимых элементов
 * (у которых [SettingsParamType.isVisible] == true) из списка таких элементов
 */

/**
 * [En]
 * This extension function is designed to get the number of visible elements
 * (which have [SettingsParamType.isVisible] == true) from the list of such elements
 */
internal inline val List<SettingsParamType>.visibleParamsSize : Int
    get() {
        return this.filter { it.isVisible }.size
    }

internal inline val List<SettingsParamType>.visibleParamsLastIndex : Int
    get() = this.filter { it.isVisible }.lastIndex

fun LocalTime.hourToString() : String {
    val hour = hour

    return if(hour in 0..9) "0$hour" else hour.toString()
}

fun LocalTime.minuteToString() : String {
    val minute = minute

    return if(minute in 0..9) "0$minute" else minute.toString()
}