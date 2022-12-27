package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import android.graphics.Bitmap
import java.io.File

/**
 * [Ru]
 * Интерфейс для управления хранимых изображений
 */
/**
 * [En]
 * Interface to manage stored images
 */
interface ImageRepository {

    /**
     * [Ru]
     * Данный метод предоставляет путь, где должно хранится изображение события
     */
    /**
     * [En]
     * This method provides the path where the event image should be stored
     */
    fun getSaveImageFile(eventId:Int) : File

    /**
     * [Ru]
     * Удаляет изображение по его id
     */
    /**
     * [En]
     * Removes an image by its id
     */
    suspend fun removeImage(eventId: Int)

    /**
     * [Ru]
     * Предоставляет изображения события, если такое имеется
     */
    /**
     * [En]
     * Provides images of the event, if available
     */
    suspend fun getEventBitmap(eventId: Int) : Bitmap?
}