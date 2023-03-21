package com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases

interface IsNowWorkTimeCheckUseCase {
    suspend fun execute() : Boolean
}