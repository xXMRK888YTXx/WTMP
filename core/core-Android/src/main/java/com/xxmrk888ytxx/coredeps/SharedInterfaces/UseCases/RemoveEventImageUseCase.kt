package com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases

interface RemoveEventImageUseCase {
    suspend fun execute(eventId:Int)
}