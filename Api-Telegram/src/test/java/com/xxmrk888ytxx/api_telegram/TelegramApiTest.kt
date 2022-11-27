package com.xxmrk888ytxx.api_telegram

import android.graphics.Bitmap
import com.xxmrk888ytxx.api_telegram.models.TelegramRequestResult
import com.xxmrk888ytxx.coredeps.Exceptions.TelegramCancelMessage
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepository
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TelegramApiTest {
    private val mockWebServer = MockWebServer()

    private lateinit var telegramMockServerRepository: TelegramRepository
    private lateinit var telegramThrowableRepository: TelegramRepository

    val testBotKey = "test-key"
    val testUserId = 21321234L



    @Before
    fun init() {
        val apiMockServer = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TelegramApi::class.java)
        telegramMockServerRepository = TelegramRepositoryImpl(apiMockServer,testBotKey,testUserId)
        telegramThrowableRepository = TelegramRepositoryImpl(
            object : TelegramApi {
                fun getThrowable(text:String) {
                    when(text) {
                        "UnknownHostException" -> throw UnknownHostException()
                        else -> error("")
                    }
                }

                override suspend fun sendMessage(
                    botKey: String,
                    userId: Long,
                    text: String,
                ): Response<TelegramRequestResult> {
                    getThrowable(text)
                    TODO()
                }

                override suspend fun sendPhoto(
                    botKey: String,
                    userId: Long,
                    caption: String,
                    body: MultipartBody.Part,
                ): Response<TelegramRequestResult> {
                    getThrowable(caption)
                    TODO()
                }

            }

            ,testBotKey,testUserId)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `send message to telegram`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testOkResponse).setResponseCode(200))
        telegramMockServerRepository.sendMessage("test")
    }

    @Test
    fun `send message to telegram if telegram cancel sendMessage request expect throw TelegramCancelMessage and body in message`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testFailResponse).setResponseCode(403))
        try {
            telegramMockServerRepository.sendMessage("test")
            Assert.fail("sendMessage method completed without exception")
        }catch (e:Exception) {
            Assert.assertEquals(TelegramCancelMessage::class.java,e::class.java)
            Assert.assertEquals(testFailResponse,e.message)
        }
    }

    @Test
    fun `send message expect path is correct`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testOkResponse).setResponseCode(203))
        val text = "text"

        telegramMockServerRepository.sendMessage(text)

        val requestPath = mockWebServer.takeRequest().path
        Assert.assertEquals("/bot$testBotKey/sendMessage?&chat_id=$testUserId&text=$text",requestPath)
    }

    @Test(expected = UnknownHostException::class)
    fun `send message expect exception in sending request process`() = runBlocking  {
       telegramThrowableRepository.sendMessage("UnknownHostException")
    }

    @Test
    fun `send image to telegram`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testOkResponse).setResponseCode(200))
        telegramMockServerRepository.sendPhoto(getTestBitmap())
    }

    @Test
    fun `send message to telegram if telegram cancel sendPhoto request expect throw TelegramCancelMessage and body in message`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testFailResponse).setResponseCode(403))
        try {
            telegramMockServerRepository.sendPhoto(getTestBitmap())
            Assert.fail("sendPhoto method completed without exception")
        }catch (e:Exception) {
            Assert.assertEquals(TelegramCancelMessage::class.java,e::class.java)
            Assert.assertEquals(testFailResponse,e.message)
        }
    }

    @Test
    fun `send photo expect path is correct`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(testOkResponse).setResponseCode(203))
        val text = "text"

        telegramMockServerRepository.sendPhoto(getTestBitmap(),text)

        val requestPath = mockWebServer.takeRequest().path
        Assert.assertEquals("/bot$testBotKey/sendPhoto?&chat_id=$testUserId&caption=$text",requestPath)
    }

    @Test(expected = UnknownHostException::class)
    fun `send photo expect exception in sending request process`() = runBlocking  {
        telegramThrowableRepository.sendPhoto(getTestBitmap(),"UnknownHostException")
    }


    private fun getTestBitmap(): Bitmap = mockk(relaxed = true)


    private val testFailResponse = "{\"ok\":false,\"error_code\":403," +
            "\"description\":\"Forbidden: bot was blocked by the user\"}"

    private val testOkResponse = "{\n" +
            "    \"ok\": true,\n" +
            "    \"result\": {\n" +
            "        \"message_id\": 119,\n" +
            "        \"from\": {\n" +
            "            \"id\": 5809437736,\n" +
            "            \"is_bot\": true,\n" +
            "            \"first_name\": \"MazytaLegendaBot\",\n" +
            "            \"username\": \"mazytaLEGENDA_bot\"\n" +
            "        },\n" +
            "        \"chat\": {\n" +
            "            \"id\": 599078884,\n" +
            "            \"first_name\": \"Артём\",\n" +
            "            \"last_name\": \"Жак\",\n" +
            "            \"username\": \"xXMRK888YTXx\",\n" +
            "            \"type\": \"private\"\n" +
            "        },\n" +
            "        \"date\": 1669187405,\n" +
            "        \"text\": \"С кайфом\"\n" +
            "    }\n" +
            "}"
}