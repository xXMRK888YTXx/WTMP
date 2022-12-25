package com.xxmrk888ytxx.observer.ImageRepository

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.ImageRepository
import com.xxmrk888ytxx.observer.domain.ImageRepository.ImageRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
class ImageRepositoryTest {
    private val context: Context by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    private val repo:ImageRepository = ImageRepositoryImpl(context)

    private val testEventId = 100000

    @After
    fun clear() {
        testFileForCheck(testEventId).delete()
    }

    @Test
    fun createImagePathAndGetTheyFromRepoExpectIsEquals() {
        val testId = (1..5).toList()
        val testPath = testId.map { testFileForCheck(it) }

        val pathFromRepo = testId.map { repo.getSaveImageFile(it) }

        Assert.assertEquals(testPath,pathFromRepo)
    }

    @Test
    fun addBitmapAndGetTheyFromRepoExpectReturnsEqualsBitmap() = runBlocking {
        Assert.assertEquals(null,repo.getEventBitmap(testEventId))
        val testBitmap = getTestBitmap()
        val fos = FileOutputStream(testFileForCheck(testEventId))
        testBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
        fos.close()

        val bitmapFromRepo = repo.getEventBitmap(testEventId)

        Assert.assertEquals(testBitmap.height,bitmapFromRepo?.height)
        Assert.assertEquals(testBitmap.width,bitmapFromRepo?.width)
    }

    @Test
    fun addImageAndRemoveTheyAndGetImageExpectReturnsNull() = runBlocking() {
        Assert.assertEquals(null,repo.getEventBitmap(testEventId))
        val testBitmap = getTestBitmap()
        val fos = FileOutputStream(testFileForCheck(testEventId))
        testBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
        fos.close()

        Assert.assertNotEquals(null, repo.getEventBitmap(testEventId))
        repo.removeImage(testEventId)

        Assert.assertEquals(null, repo.getEventBitmap(testEventId))
    }

    private fun testFileForCheck(eventId:Int) : File {
        val contextWrapper = ContextWrapper(context)
        val dir =  contextWrapper.getDir("ImageDir", Context.MODE_PRIVATE).absolutePath
        return File(dir,"image-$eventId.jpg")
    }

    private fun getTestBitmap() : Bitmap {
        return Bitmap.createBitmap(8,8,Bitmap.Config.RGB_565)
    }


}