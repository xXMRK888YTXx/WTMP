package com.xxmrk888ytxx.coredeps.SharedInterfaces

import kotlinx.coroutines.flow.Flow

interface AppStateProvider {
    val isAppEnable: Flow<Boolean>
}