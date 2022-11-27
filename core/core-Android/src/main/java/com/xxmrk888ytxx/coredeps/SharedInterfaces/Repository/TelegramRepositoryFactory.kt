package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository


/**
 * [Ru]
 * Данный интерфейс реалезует создание [TelegramRepository]
 * @param botKey - ключ бота Telegram
 * @param userId - id пользователя
 * [En]
 * This interface implements the creation of [TelegramRepository]
 * @param botKey - Telegram bot key
 * @param userId - user id
 */
interface TelegramRepositoryFactory {
    fun create(botKey:String,userId:Long) : TelegramRepository
}