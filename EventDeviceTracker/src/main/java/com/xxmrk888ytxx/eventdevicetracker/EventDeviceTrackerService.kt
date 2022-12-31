package com.xxmrk888ytxx.eventdevicetracker

import android.accessibilityservice.AccessibilityService
import android.content.*
import android.view.accessibility.AccessibilityEvent
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

internal class EventDeviceTrackerService : AccessibilityService() {

    private var lastTrackedPackageName = ""

    private val eventDeviceTrackerCallback by lazy {
        applicationContext.getDepsByApplication<EventDeviceTrackerCallback>()
    }

    private val receiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent) {
                if(intent.action == Intent.ACTION_USER_PRESENT)
                    eventDeviceTrackerCallback.onScreenOn()
            }
        }
    }

    override fun onCreate() {
        registerReceiver(receiver, IntentFilter(Intent.ACTION_USER_PRESENT))
        super.onCreate()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if(!isEventValid(event)) return

        lastTrackedPackageName = event.packageName.toString()
        eventDeviceTrackerCallback.onOpenAppChanged(event.packageName.toString())
    }

    override fun onInterrupt() {}

    private fun isEventValid(event: AccessibilityEvent) : Boolean {
        val isCorrectType = event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        val isNotIgnorePackage = event.packageName !in eventDeviceTrackerCallback.params.ignoreList
        val isNewPackage = lastTrackedPackageName != event.packageName
        val isActivity = if(!eventDeviceTrackerCallback.params.isActivityOnly) true
            else isActivity(event)
        return isCorrectType && isNotIgnorePackage && isNewPackage && isActivity
    }

    private fun isActivity(event: AccessibilityEvent): Boolean {
        return try {
            val component = ComponentName(event.packageName.toString(),event.className.toString())

            packageManager.getActivityInfo(component,0).isEnabled
        } catch (e:Exception) {
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        eventDeviceTrackerCallback.onServiceDestroy()
    }
}