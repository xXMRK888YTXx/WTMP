package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

interface AppPasswordChanger {

    suspend fun setupAppPassword(oldPassword:String?,newPassword:String)
}