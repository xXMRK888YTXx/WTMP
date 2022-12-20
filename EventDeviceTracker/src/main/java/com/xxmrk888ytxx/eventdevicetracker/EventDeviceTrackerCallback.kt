package com.xxmrk888ytxx.eventdevicetracker

interface EventDeviceTrackerCallback {
    val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().build()

    fun onOpenAppChanged(packageName:String)
}