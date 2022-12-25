package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import android.graphics.Bitmap
import java.io.File

interface ImageRepository {

    fun getSaveImageFile(eventId:Int) : File

    suspend fun removeImage(eventId: Int)

    suspend fun getEventBitmap(eventId: Int) : Bitmap?
}