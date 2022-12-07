package com.xxmrk888ytxx.settingsscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(): ViewModel() {
    val testParam = mutableStateOf(false)
}