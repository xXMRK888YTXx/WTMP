package com.xxmrk888ytxx.observer.domain

import android.util.Log
import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerCallback
import com.xxmrk888ytxx.eventdevicetracker.EventDeviceTrackerParams
import javax.inject.Inject

class EventDeviceTrackerCallbackImpl @Inject constructor(

): EventDeviceTrackerCallback {
    override val params: EventDeviceTrackerParams
        get() = EventDeviceTrackerParams.Builder().setIgnoreList(ignoreList).build()

    override fun onOpenAppChanged(packageName: String) {
        Log.d("MyLog",packageName)
    }

    override fun onScreenOn() {
        logcatMessageD("Screen on")
    }

    private val ignoreList:List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}