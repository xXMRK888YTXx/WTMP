package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState


interface AppStateChanger {
    suspend fun updateAppState(state:Boolean)
}