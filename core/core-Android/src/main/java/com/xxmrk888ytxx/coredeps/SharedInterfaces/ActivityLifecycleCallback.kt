package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.app.Activity

interface ActivityLifecycleCallback {
    fun onCreate(activity: Activity) {}

    fun onStart() {}

    fun onResume() {}

    fun onPause() {}

    fun onStop() {}

    fun onDestroy() {}

    fun onRegister(activity:Activity) {}
}