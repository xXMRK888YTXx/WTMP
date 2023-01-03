package com.xxmrk888ytxx.setupapppasswordscreen

internal sealed class ScreenMode {
    data class SetupPassword(
        val inputtedFirstPassword:String? = null
    ) : ScreenMode()

    object RemovePassword : ScreenMode()

    object None : ScreenMode()
}