package com.xxmrk888ytxx.observer.domain.ImageRepository

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {

    override fun getSaveImageFile(eventId: Int): File {
        return File(imageDir,getImageName(eventId))
    }

    override suspend fun removeImage(eventId: Int) {
        withContext(Dispatchers.IO) {
            val imageFile = File(imageDir,getImageName(eventId))

            if(imageFile.exists()) {
                imageFile.delete()
            }
        }
    }

    override suspend fun getEventBitmap(eventId: Int): Bitmap? {
        return try {
            BitmapFactory.decodeFile(getSaveImageFile(eventId).absolutePath)
        }catch (e:Exception) {
            null
        }
    }

    private fun getImageName(eventId: Int) = "image-$eventId.jpg"

    private val imageDir : String
       get() {
           val contextWrapper = ContextWrapper(context)
           return contextWrapper.getDir("ImageDir", Context.MODE_PRIVATE).absolutePath
       }
}