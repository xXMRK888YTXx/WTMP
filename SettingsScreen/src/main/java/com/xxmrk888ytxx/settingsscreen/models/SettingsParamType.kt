package com.xxmrk888ytxx.settingsscreen.models

import androidx.annotation.IdRes

internal sealed class SettingsParamType(
    open val text:String,
    @IdRes open val icon:Int,
    open val isEnable:Boolean,
    open val isVisible:Boolean
) {

    data class CheckBox(
        override val text:String,
        @IdRes override val icon:Int,
        val isChecked: Boolean,
        override val isEnable: Boolean = true,
        override val isVisible: Boolean = true,
        val onStateChanged:(Boolean) -> Unit
    ) : SettingsParamType(text,icon,isEnable,isVisible)

    data class Button(
        override val text:String,
        @IdRes override val icon:Int,
        override val isEnable: Boolean = true,
        override val isVisible: Boolean = true,
        val onClick:() -> Unit
    ) : SettingsParamType(text,icon,isEnable,isVisible)
}