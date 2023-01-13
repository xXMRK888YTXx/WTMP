package com.xxmrk888ytxx.coredeps.models

/**
 * [Ru]
 * Настройки для приложения, которые настраивает отслеживание запуска устройства
 * @param isTracked - Параметр отвечает, отслеживается выбранные приложения
 * @param makePhoto - Делать ли фотографию, при успешном отслеживании
 * @param notifyInTelegram - отправлять ли уведомление в Telegram, при успешном отслеживании
 * @param joinPhotoToTelegramNotify - прикреплять ли фотографию к уведомлению в Telegram
 */
/**
 * [En]
 * Settings for the app that configures device startup tracking
 * @param isTracked - Parameter is responding, selected applications are being tracked
 * @param makePhoto - Whether to make a photo if tracking is successful
 * @param notifyInTelegram - whether to send a notification to Telegram on successful tracking
 * @param joinPhotoToTelegramNotify - whether to attach a photo to a notification in Telegram
 */
data class BootDeviceTrackedConfig(
    val isTracked:Boolean,
    val makePhoto:Boolean,
    val notifyInTelegram:Boolean,
    val joinPhotoToTelegramNotify:Boolean
)