package com.xxmrk888ytxx.coredeps.SharedInterfaces

/**
 * [Ru]
 *  Данный интерфейс предназначен для запуска Worker`ов
 * [En]
 *  This interface is designed to run Workers
 */
interface WorkerManager {
    /**
     * [Ru]
     * Запускает воркер для отправки сообщения в Telegram
     */
    /**
     * [En]
     * Starts a worker to send a message to Telegram
     */
    fun sendMessageTelegram(botKey:String,userId:Long,message:String)

    /**
     * [Ru]
     * Запускает воркер для отправки сообщения с изображением в Telegram
     */
    /**
     * [En]
     * Starts a worker to send a message with an image to Telegram
     */
    fun sendPhotoTelegram(botKey:String,userId:Long,photoPath:String?,caption:String = "")

    /**
     * [Ru]
     * Запускает воркер для создания изображения с фронтальной камеры
     */
    /**
     * [En]
     * Launches a worker to create an image from the front camera
     */
    fun createImageWorker(imageDir:String)

    /**
     * [Ru]
     * Позволяет запустить несколько воркеров путём
     * вызова соотвествующих воркеров в передоваемой лямбде
     */
    /**
     * [En]
     * Allows you to run multiple workers by
     * calling the appropriate workers in the passed lambda
     */
    suspend fun createMultiRequest(request:suspend WorkerManager.() -> Unit)
}