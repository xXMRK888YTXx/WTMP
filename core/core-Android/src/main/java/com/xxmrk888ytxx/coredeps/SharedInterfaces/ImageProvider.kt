package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.graphics.Bitmap

/**
 * [Ru]
 * Данный интерфейс предназначен для открытия созданных
 * приложением изображений в галлере устройства
 */
/**
 * [En]
 * This interface is designed to open created
 * app images in device gallery
 */
interface ImageProvider {

    suspend fun provideImageToGallery(bitmap: Bitmap)

    suspend fun clearShareDir()

}