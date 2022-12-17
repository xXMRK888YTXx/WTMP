package com.xxmrk888ytxx.observer.domain

import android.util.Log
import com.xxmrk888ytxx.openapptracker.AppOpenTrackerCallback
import javax.inject.Inject

class AppOpenTrackerCallbackImpl @Inject constructor(

): AppOpenTrackerCallback {
    override val ignoreList: List<String>
        get() = listOf("com.android.systemui")

    override fun onOpenAppChanged(packageName: String) {
        Log.d("MyLog",packageName)
    }
}