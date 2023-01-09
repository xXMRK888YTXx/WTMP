package com.xxmrk888ytxx.observer.domain.ImageProvider

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ImageProvider
import com.xxmrk888ytxx.coredeps.logcatMessageD
import com.xxmrk888ytxx.observer.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

internal class ImageProviderImpl @Inject constructor(
    private val context:Context
) : ImageProvider {

    private val shareDir : File
        get() = File(context.cacheDir,"provideImages")

    override suspend fun provideImageToGallery(bitmap: Bitmap) {
        try {
            shareDir.mkdir()
            val outputFile = File(shareDir,"shareFile.jpg")
            withContext(Dispatchers.IO) {
                val fos = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos)
                fos.close()
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID,outputFile),
                "image/*"
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(intent)
        }catch (e:Exception) {
            logcatMessageD(e.stackTraceToString())
        }
    }

    override suspend fun clearShareDir() {
        try {
            shareDir.deleteRecursively()
        }catch (_:Exception) {}
    }
}