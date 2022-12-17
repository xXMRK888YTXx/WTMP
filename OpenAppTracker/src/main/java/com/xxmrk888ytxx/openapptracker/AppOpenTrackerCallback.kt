package com.xxmrk888ytxx.openapptracker

interface AppOpenTrackerCallback {
    val ignoreList:List<String>

    fun onOpenAppChanged(packageName:String)
}