package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import java.io.File

interface ImageRepository {

    fun getSaveImageFile(eventId:Int) : File

    suspend fun removeImage(eventId: Int)
}