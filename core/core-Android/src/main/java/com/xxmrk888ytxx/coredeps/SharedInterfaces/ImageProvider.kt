package com.xxmrk888ytxx.coredeps.SharedInterfaces

import android.graphics.Bitmap

interface ImageProvider {

    suspend fun provideImageToGallery(bitmap: Bitmap)

    suspend fun clearShareDir()

}