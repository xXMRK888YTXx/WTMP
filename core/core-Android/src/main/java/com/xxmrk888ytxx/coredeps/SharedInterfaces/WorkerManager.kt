package com.xxmrk888ytxx.coredeps.SharedInterfaces

/**
 * [Ru]
 *  Данный интерфейс предназначен для запуска Worker`ов
 * [En]
 *  This interface is designed to run Workers
 */
interface WorkerManager {
    fun sendMessageTelegram(botKey:String,userId:Long,message:String)

    fun sendPhotoTelegram(botKey:String,userId:Long,photoPath:String?,caption:String = "")

    fun createImageWorker(imageDir:String)

    suspend fun createMultiRequest(request:suspend WorkerManager.() -> Unit)
}