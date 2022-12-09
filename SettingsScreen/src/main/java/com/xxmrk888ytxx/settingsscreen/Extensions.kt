package com.xxmrk888ytxx.settingsscreen

import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType

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
internal val List<SettingsParamType>.visibleParamsSize : Int
    get() {
        return this.filter { it.isVisible }.size
    }