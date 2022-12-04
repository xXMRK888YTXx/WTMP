package com.xxmrk888ytxx.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coredeps.SharedInterfaces.PackageInfoProvider
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val packageInfoProvider: PackageInfoProvider
) : ViewModel() {
    val isEnable = mutableStateOf(true)

    private val testRealAppInfo by lazy {
        DeviceEvent.AppOpen(
            7,
            packageInfoProvider.getAppName("com.xxmrk888ytxx.privatenote") ?: "Ошибка",
            packageInfoProvider.getAppIcon("com.xxmrk888ytxx.privatenote")?.toBitmap(),
            System.currentTimeMillis()
        )
    }

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
        ),
        testRealAppInfo
    )
}