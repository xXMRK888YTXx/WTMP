package com.xxmrk888ytxx.coredeps.models

/**
 * [Ru]
 * Настройки для приложения, что делать
 * при отслеженной неудачной попытки входа
 * @param isTracked - Параметр отвечает, отслеживается ли неправильные попытки ввода пароля
 * @param makePhoto - Делать ли фотографию, при успешном отслеживании
 * @param notifyInTelegram - отправлять ли уведомление в Telegram, при успешном отслеживании
 * @param joinPhotoToTelegramNotify - прикреплять ли фотографию к уведомлению в Telegram
 */
/**
 * [En]
 * Settings for the app what to do
 * when a failed login attempt is tracked
 * @param isTracked - Parameter tells if invalid password attempts are tracked
 * @param makePhoto - Whether to make a photo if tracking is successful
 * @param notifyInTelegram - whether to send notifications to Telegram on successful tracking
 * @param joinPhotoToTelegramNotify - whether to attach a photo to a notification in Telegram
 */
data class FailedUnlockTrackedConfig(
    val isTracked:Boolean,
    val makePhoto:Boolean,
    val notifyInTelegram:Boolean,
    val joinPhotoToTelegramNotify:Boolean
)