package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.app.Notification

/**
 * [EN]
 * Interface for managing and sending application-specific notifications.
 * This manager is responsible for creating and displaying notifications
 *
 * [RU]
 * Интерфейс для управления и отправки специфичных для приложения уведомлений.
 * Этот менеджер отвечает за создание и отображение уведомлений
 */
interface NotificationAppManager {
    fun sendAdminPermissionWithdrawnNotification()

    fun sendAccessibilityPermissionWithdrawnNotification()

    fun sendSomePermissionWasWithdrawnNotification()

    val notificationForServiceWorker: Notification
}