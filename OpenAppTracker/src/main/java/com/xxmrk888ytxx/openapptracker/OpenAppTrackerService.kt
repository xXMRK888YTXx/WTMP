package com.xxmrk888ytxx.openapptracker

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.view.accessibility.AccessibilityEvent
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

internal class OpenAppTrackerService : AccessibilityService() {

    private var lastTrackedPackageName = ""

    private val appOpenTrackerCallback by lazy {
        applicationContext.getDepsByApplication<AppOpenTrackerCallback>()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if(!isEventValid(event)) return

        lastTrackedPackageName = event.packageName.toString()
        appOpenTrackerCallback.onOpenAppChanged(event.packageName.toString())
    }

    override fun onInterrupt() {}

    private fun isEventValid(event: AccessibilityEvent) : Boolean {
        val isCorrectType = event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        val isNotIgnorePackage = event.packageName !in appOpenTrackerCallback.params.ignoreList
        val isNewPackage = lastTrackedPackageName != event.packageName
        val isActivity = if(!appOpenTrackerCallback.params.isActivityOnly) true
            else isActivity(event)
        return isCorrectType && isNotIgnorePackage && isNewPackage && isActivity
    }

    private fun isActivity(event: AccessibilityEvent): Boolean {
        try {
        val component = ComponentName(event.packageName.toString(),event.className.toString())

        return packageManager.getActivityInfo(component,0).isEnabled
        } catch (e:Exception) {
            return false
        }
    }
}