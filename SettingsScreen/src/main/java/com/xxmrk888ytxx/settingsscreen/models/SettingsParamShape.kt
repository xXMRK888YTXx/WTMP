package com.xxmrk888ytxx.settingsscreen.models

internal sealed class SettingsParamShape {

    object AllShape : SettingsParamShape()

    object TopShape : SettingsParamShape()

    object BottomShape : SettingsParamShape()

    object None : SettingsParamShape()
}