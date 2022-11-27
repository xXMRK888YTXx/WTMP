package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface WorkerManager {
    fun sendMessageTelegram(botKey:String,userId:Long,message:String)

    fun sendPhotoTelegram(botKey:String,userId:Long,photoPath:String,caption:String = "")
}