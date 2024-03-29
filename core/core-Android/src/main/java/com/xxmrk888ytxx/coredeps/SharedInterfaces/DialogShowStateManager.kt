package com.xxmrk888ytxx.coredeps.SharedInterfaces

import kotlinx.coroutines.flow.Flow

interface DialogShowStateManager {
    suspend fun setupIgnoreIgnoreBatteryOptimisationDialogShowState(state:Boolean)

    val isIgnoreIgnoreBatteryOptimisationDialogNeedShow : Flow<Boolean>

    suspend fun setupAgreementDialog(state: Boolean)

    val isAgreementDialogNeedShow : Flow<Boolean>
}