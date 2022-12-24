package com.xxmrk888ytxx.observer.domain.ImageRepository

import android.content.Context
import android.content.ContextWrapper
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {

    override fun getSaveImageFile(eventId: Int): File {
        return File(imageDir,"image-$eventId.jpg")
    }

    override suspend fun removeImage(eventId: Int) {
        withContext(Dispatchers.IO) {
            val imageFile = File(imageDir,"image-$eventId.jpg")

            if(imageFile.exists()) {
                imageFile.delete()
            }
        }
    }

    private val imageDir : String
       get() {
           val contextWrapper = ContextWrapper(context)
           return contextWrapper.getDir("ImageDir", Context.MODE_PRIVATE).absolutePath
       }
}