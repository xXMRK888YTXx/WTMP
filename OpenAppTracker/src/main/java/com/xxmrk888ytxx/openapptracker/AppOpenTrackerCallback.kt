package com.xxmrk888ytxx.openapptracker

interface AppOpenTrackerCallback {
    val params: OpenAppTrackerParams
        get() = OpenAppTrackerParams.Builder().build()

    fun onOpenAppChanged(packageName:String)
}