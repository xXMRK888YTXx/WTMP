package com.xxmrk888ytxx.settingsscreen

import com.xxmrk888ytxx.settingsscreen.models.SettingsParamType

internal val List<SettingsParamType>.visibleParamsSize : Int
    get() {
        return this.filter { it.isVisible }.size
    }