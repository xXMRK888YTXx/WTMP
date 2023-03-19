package com.xxmrk888ytxx.coredeps.SharedInterfaces

interface Paginator<KEY,VALUE> {

    suspend fun load()

    fun resetPage()
}