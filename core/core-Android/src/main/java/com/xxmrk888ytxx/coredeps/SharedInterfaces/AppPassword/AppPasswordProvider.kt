package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

import kotlinx.coroutines.flow.Flow

interface AppPasswordProvider {

    suspend fun isAppPassword(checkingPassword:String) : Boolean

    fun isPasswordSetup() : Flow<Boolean>

}