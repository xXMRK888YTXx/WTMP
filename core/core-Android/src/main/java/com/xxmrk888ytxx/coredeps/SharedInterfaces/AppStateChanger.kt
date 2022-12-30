package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface AppStateChanger {
    suspend fun updateAppState(state:Boolean)
}