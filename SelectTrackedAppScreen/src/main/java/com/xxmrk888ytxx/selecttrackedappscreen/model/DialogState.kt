package com.xxmrk888ytxx.selecttrackedappscreen.model

sealed interface DialogState {
    data class AddPackageDialog(val packageName: String) : DialogState
    object None : DialogState
}