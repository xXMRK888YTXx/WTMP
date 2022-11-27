package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

interface TelegramRepositoryFactory {
    fun create(botKey:String,userId:Long) : TelegramRepository
}