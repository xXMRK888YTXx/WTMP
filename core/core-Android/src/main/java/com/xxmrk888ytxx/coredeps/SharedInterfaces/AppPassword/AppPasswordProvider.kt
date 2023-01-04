package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

import kotlinx.coroutines.flow.Flow

interface AppPasswordProvider {

    suspend fun isAppPassword(checkingPassword:String) : Boolean

    fun isPasswordSetupFlow() : Flow<Boolean>

    fun isFingerPrintAuthorizationEnabled() : Flow<Boolean>

}