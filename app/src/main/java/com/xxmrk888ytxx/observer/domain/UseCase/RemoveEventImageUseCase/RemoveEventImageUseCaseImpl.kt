package com.xxmrk888ytxx.observer.domain.UseCase.RemoveEventImageUseCase

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.UseCases.RemoveEventImageUseCase
import com.xxmrk888ytxx.coredeps.logcatMessageD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RemoveEventImageUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : RemoveEventImageUseCase {

    override suspend fun execute(eventId: Int) {
        withContext(Dispatchers.IO) {
            try {
                imageRepository.removeImage(eventId)
            }catch (e:Exception) {
                logcatMessageD(e.stackTraceToString())
            }
        }
    }

}