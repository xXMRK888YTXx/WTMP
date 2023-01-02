package com.xxmrk888ytxx.enterpasswordscreen.models

import androidx.annotation.IdRes

internal sealed class GridButtonType {
    data class NumberButton(
        val number: Int
    ) : GridButtonType()

    data class ActionButton(
        @IdRes val icon:Int,
        val onClick:() -> Unit
    ) : GridButtonType()

    object Stub : GridButtonType()
}
