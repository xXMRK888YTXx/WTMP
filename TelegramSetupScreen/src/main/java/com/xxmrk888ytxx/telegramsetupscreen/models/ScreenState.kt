package com.xxmrk888ytxx.telegramsetupscreen.models

internal sealed class ScreenState {
    object ChangeTelegramConfigState : ScreenState()

    object ConfigSavedState : ScreenState()
}
