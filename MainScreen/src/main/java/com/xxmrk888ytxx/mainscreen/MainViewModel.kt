package com.xxmrk888ytxx.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel() {
    val isEnable = mutableStateOf(true)

    val eventList = mutableListOf(
        DeviceEvent.AttemptUnlockDevice.Failed(1,System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Failed(2,System.currentTimeMillis()+10000),
        DeviceEvent.AttemptUnlockDevice.Succeeded(3,System.currentTimeMillis()),
        DeviceEvent.AttemptUnlockDevice.Succeeded(4,System.currentTimeMillis()+10000),
        DeviceEvent.AppOpen(
            5,"Brawl Stars",null,System.currentTimeMillis()
        ),
        DeviceEvent.AppOpen(
            6,"MineCraft",null,System.currentTimeMillis()
        )
    )
}