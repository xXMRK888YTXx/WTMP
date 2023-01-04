package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

interface AppPasswordChanger {

    suspend fun setupAppPassword(password:String)

    suspend fun removePassword(currentPassword:String)
}