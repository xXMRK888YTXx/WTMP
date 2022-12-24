package com.xxmrk888ytxx.observer.domain.EventDeviceTrackerCallback

import android.util.Log
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.DeviceEventRepository
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerParams
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventDeviceTrackerCallbackImpl @Inject constructor(
    private val deviceEventRepository: DeviceEventRepository
): EventDeviceTrackerCallback {
    override val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().setIgnoreList(ignoreList).build()

    override fun onOpenAppChanged(packageName: String) {
        Log.d("MyLog",packageName)
    }

    override fun onScreenOn() {
        ApplicationScope.launch {
            deviceEventRepository.addEvent(
                DeviceEvent.AttemptUnlockDevice.Succeeded(0,System.currentTimeMillis())
            )
        }
    }

    private val ignoreList:List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}