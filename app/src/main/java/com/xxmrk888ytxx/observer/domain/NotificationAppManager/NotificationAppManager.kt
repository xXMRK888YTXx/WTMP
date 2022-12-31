package com.xxmrk888ytxx.observer.domain.NotificationAppManager

interface NotificationAppManager {
    fun sendAdminPermissionWithdrawnNotification()

    fun sendAccessibilityPermissionWithdrawnNotification()
}