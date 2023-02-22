package com.xxmrk888ytxx.adutils

import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Интерфейс который управляет показом рекламы
 */
/**
 * [En]
 * Interface that controls the display of ads
 */
interface AdStateManager {

    suspend fun changeAdState(state:Boolean)

    val isNeedShowAd : Flow<Boolean>
}