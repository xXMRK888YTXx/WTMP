package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface ActivityLifecycleRegister {
    fun registerCallback(activityLifecycleCallback: ActivityLifecycleCallback)

    fun unregisterCallback(activityLifecycleCallback: ActivityLifecycleCallback)
}