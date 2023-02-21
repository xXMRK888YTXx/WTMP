package com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases

/**
 * [Ru]
 * UseCase который вызывается для удаления фотографии прекриплённой к отчёту
 */
/**
   [En]
   UseCase that is called to delete a photo attached to a report
 */
interface RemoveEventImageUseCase {
    suspend fun execute(eventId: Int)
}