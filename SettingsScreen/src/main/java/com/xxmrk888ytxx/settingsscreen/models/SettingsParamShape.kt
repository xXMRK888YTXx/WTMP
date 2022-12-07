package com.xxmrk888ytxx.settingsscreen.models

sealed class SettingsParamShape {
    object TopShape : SettingsParamShape()

    object BottomShape : SettingsParamShape()

    object None : SettingsParamShape()
}