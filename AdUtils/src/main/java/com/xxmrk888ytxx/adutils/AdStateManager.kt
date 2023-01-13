package com.xxmrk888ytxx.adutils

import kotlinx.coroutines.flow.Flow

interface AdStateManager {

    suspend fun changeAdState(state:Boolean)

    val isNeedShowAd : Flow<Boolean>
}