package com.xxmrk888ytxx.settingsscreen.models

import androidx.annotation.IdRes

sealed class SettingsParamType(open val text:String,@IdRes open val icon:Int) {

    data class CheckBox(
        override val text:String,
        @IdRes override val icon:Int,
        val isChecked: Boolean,
        val onStateChanged:(Boolean) -> Unit
    ) : SettingsParamType(text,icon)

    data class Button(
        override val text:String,
        @IdRes override val icon:Int,
        val onClick:() -> Unit
    ) : SettingsParamType(text, icon)
}