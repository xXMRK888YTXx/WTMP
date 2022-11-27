package com.xxmrk888ytxx.workers.DI

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.TelegramRepositoryFactory

interface WorkerComponentDeps {
    val telegramRepositoryFactory:TelegramRepositoryFactory
}