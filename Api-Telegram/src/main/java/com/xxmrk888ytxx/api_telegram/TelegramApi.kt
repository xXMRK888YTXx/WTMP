package com.xxmrk888ytxx.api_telegram

import com.xxmrk888ytxx.api_telegram.models.TelegramRequestResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

internal interface TelegramApi {
    /**
     * [Ru]
     * Данная функция используется для отправки сообщение в телеграмм
     * @param botKey - Ключ необходимый для отправки команд боту
     * @param userId - Id полозователя которому нужно доставить сообщение
     * @param text - Текст сообщения
     *
     * [En]
     * This function is used to send a message to telegram
     * @param botKey - The key required to send commands to the bot
     * @param userId - Id of the user to which the message should be delivered
     * @param text - Message text
     */
    @GET("/bot{botKey}/sendMessage?")
    suspend fun sendMessage(
        @Path("botKey",encoded = true)botKey:String,
        @Query("chat_id",encoded = true)userId:Long,
        @Query("text",encoded = true)text:String) : Response<TelegramRequestResult>

    /**
     * [Ru]
     * Данная функция используется для отправки сообщение с изображением в телеграмм
     * @param botKey - Ключ необходимый для отправки команд боту
     * @param userId - Id полозователя которому нужно доставить сообщение
     * @param caption - подпись сообщения, если этот параметр пустой, подписи не будет
     * @param body - состовная часть в которую зашивается изображение, с медиа типом "multipart/form-data",
     * фото должно быть меньше 10Мб
     *
     * [En]
     * This function is used to send a message with an image in telegram
     * @param botKey - The key required to send commands to the bot
     * @param userId - Id of the user to which the message should be delivered
     * @param caption - message caption, if this parameter is empty, there will be no caption
     * @param body - the component part into which the image is sewn, with the media type "multipart/form-data",
     * photo must be less than 10Mb
     */
    @POST("/bot{botKey}/sendPhoto?")
    @Multipart
    suspend fun sendPhoto(
        @Path("botKey",encoded = true)botKey:String,
        @Query("chat_id",encoded = true)userId:Long,
        @Query("caption",encoded = true)caption:String,
        @Part body:MultipartBody.Part,
    ) : Response<TelegramRequestResult>
}