package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppOpenConfig

interface AppOpenConfigChanger {
    suspend fun updateIsTracked(state:Boolean)

    suspend fun updateMakePhoto(state: Boolean)

    suspend fun updateNotifyInTelegram(state: Boolean)

    suspend fun updateJoinPhotoToTelegramNotify(state: Boolean)
}