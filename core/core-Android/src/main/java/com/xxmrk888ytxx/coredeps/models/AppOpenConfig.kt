package com.xxmrk888ytxx.coredeps.models

/**
 * [Ru]
 * Настройки для приложения, при открытии отслеживаемых приложений
 * @param isTracked - Параметр отвечает, отслеживается выбранные приложения
 * @param makePhoto - Делать ли фотографию, при успешном отслеживании
 * @param countFailedUnlockToTrigger - отвечает сколько нужно сделать проваленных попыток
 * для тогг, что бы приложение начало их отслеживать
 * @param notifyInTelegram - отправлять ли уведомление в Telegram, при успешном отслеживании
 * @param joinPhotoToTelegramNotify - прикреплять ли фотографию к уведомлению в Telegram
 */
/**
 * [En]
 * Settings for the application, when opening a cooled application
 * @param isTracked - Parameter is responding, selected applications are being tracked
 * @param makePhoto - Whether to make a photo if tracking is successful
 * @param countFailedUnlockToTrigger - tells how many failed attempts to make
 * in order for the application to start tracking them
 * @param notifyInTelegram - whether to send notifications to Telegram on successful tracking
 * @param joinPhotoToTelegramNotify - whether to attach a photo to a notification in Telegram
 */
data class AppOpenConfig(
    val isTracked:Boolean,
    val timeOperationLimit:Int,
    val makePhoto:Boolean,
    val notifyInTelegram:Boolean,
    val joinPhotoToTelegramNotify:Boolean
)