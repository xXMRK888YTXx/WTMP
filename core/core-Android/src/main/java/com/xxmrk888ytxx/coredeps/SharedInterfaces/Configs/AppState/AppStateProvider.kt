package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState

import kotlinx.coroutines.flow.Flow

interface AppStateProvider {
    val isAppEnable: Flow<Boolean>
}