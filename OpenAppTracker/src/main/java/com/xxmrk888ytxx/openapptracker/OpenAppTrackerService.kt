package com.xxmrk888ytxx.openapptracker

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

internal class OpenAppTrackerService : AccessibilityService() {

    private var lastTrackedApp = ""

    private val appOpenTrackerCallback by lazy {
        applicationContext.getDepsByApplication<AppOpenTrackerCallback>()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if(event.packageName in appOpenTrackerCallback.ignoreList||
            event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED||
                lastTrackedApp == event.packageName) return

        lastTrackedApp = event.packageName.toString()
        appOpenTrackerCallback.onOpenAppChanged(event.packageName.toString())
    }

    override fun onInterrupt() {}
}