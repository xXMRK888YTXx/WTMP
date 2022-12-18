package com.xxmrk888ytxx.observer.domain

import android.util.Log
import com.xxmrk888ytxx.openapptracker.AppOpenTrackerCallback
import com.xxmrk888ytxx.openapptracker.OpenAppTrackerParams
import javax.inject.Inject

class AppOpenTrackerCallbackImpl @Inject constructor(

): AppOpenTrackerCallback {
    override val params: OpenAppTrackerParams
        get() = OpenAppTrackerParams.Builder().setIgnoreList(ignoreList).build()

    override fun onOpenAppChanged(packageName: String) {
        Log.d("MyLog",packageName)
    }

    private val ignoreList:List<String>
        get() = listOf(
            "com.android.vending",
            "com.google.android.permissioncontroller"
        )
}