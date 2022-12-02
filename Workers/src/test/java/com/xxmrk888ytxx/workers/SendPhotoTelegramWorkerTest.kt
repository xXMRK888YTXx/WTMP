package com.xxmrk888ytxx.workers

import android.content.Context
import android.graphics.Bitmap
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory
import com.xxmrk888ytxx.workers.Workers.SendPhotoTelegramWorker
import com.xxmrk888ytxx.workers.Workers.SendTelegramMessageWorker
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SendPhotoTelegramWorkerTest {
    private lateinit var worker: SendPhotoTelegramWorker
    private val context: Context = mockk(relaxed = true)
    private val workerParams: WorkerParameters = mockk(relaxed = true)
    private val telegramRepositoryFactory: TelegramRepositoryFactory = mockk(relaxed = true)
    private val telegramRepository: TelegramRepository = mockk(relaxed = true)
    private val testBotKey = "test-bot-key"
    private val testTextParams = "e"
    private val testUserId = 123563546L
    private val photoPath = "photoPath"
    private val testBitmap:Bitmap = mockk(relaxed = true)

    @Before
    fun init() {
        worker = spyk(SendPhotoTelegramWorker(context, workerParams))
        every { context.applicationContext } returns TestApp(telegramRepositoryFactory)
        every { worker.getBitmapFromPath() } returns testBitmap
    }

    @Test
    fun `send all params in worker and send message expect worker completed work with sending in them params`() = runBlocking {
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.telegramBotKeyDataKey)} returns testBotKey
        every { workerParams.inputData.getLong(SendPhotoTelegramWorker.userIdDataKey,any())} returns testUserId
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.photoPathDataKey) } returns photoPath
        every { telegramRepositoryFactory.create(testBotKey,testUserId) } returns telegramRepository

        worker.doWork()

        verify(exactly = 1) { telegramRepositoryFactory.create(testBotKey,testUserId) }
        coVerify(exactly = 1) { telegramRepository.sendPhoto (testBitmap,any()) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `not send params and start work expect throw exception`():Unit = runBlocking {
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.telegramBotKeyDataKey)} returns null
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.photoPathDataKey) } returns null
        every { workerParams.inputData.getLong(SendTelegramMessageWorker.userIdDataKey,any())} returns -1

        worker.doWork()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `not send botKey and start work expect throw exception`():Unit = runBlocking {
        every { workerParams.inputData.getString(SendTelegramMessageWorker.telegramBotKeyDataKey)} returns null
        every { workerParams.inputData.getString(SendTelegramMessageWorker.textDataKey) } returns testTextParams
        every { workerParams.inputData.getLong(SendTelegramMessageWorker.userIdDataKey,any())} returns testUserId
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.photoPathDataKey) } returns photoPath

        worker.doWork()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `not send userId and start work expect throw exception`():Unit = runBlocking {
        every { workerParams.inputData.getString(SendTelegramMessageWorker.telegramBotKeyDataKey)} returns testBotKey
        every { workerParams.inputData.getString(SendTelegramMessageWorker.textDataKey) } returns testTextParams
        every { workerParams.inputData.getLong(SendTelegramMessageWorker.userIdDataKey,any())} returns -1
        every { workerParams.inputData.getString(SendPhotoTelegramWorker.photoPathDataKey) } returns photoPath

        worker.doWork()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `not send photoPath and start work expect throw exception`():Unit = runBlocking {
        every { workerParams.inputData.getString(SendTelegramMessageWorker.telegramBotKeyDataKey)} returns testBotKey
        every { workerParams.inputData.getString(SendTelegramMessageWorker.textDataKey) } returns testTextParams
        every { workerParams.inputData.getLong(SendTelegramMessageWorker.userIdDataKey,any())} returns -1

        worker.doWork()
    }
}