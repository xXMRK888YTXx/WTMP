package com.xxmrk888ytxx.coredeps

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Paginator
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class DefaultPaginator<KEY,VALUE>(
    private val initKey : KEY,
    private val pageSize:Int,
    private val onLoadStateChanged:(Boolean) -> Unit,
    private val onUpdateDataRequest:suspend (page:KEY,pageSize: Int) -> VALUE,
    private val onNextKey:suspend (currentKey:KEY,newData:VALUE) -> KEY?,
    private val onError:(Throwable) -> Unit,
    private val onNewPageDataLoaded:suspend (VALUE) -> Unit,
    private val onKeyReset: () -> Unit,
    private val onAllPageLoaded:() -> Unit
) : Paginator<KEY,VALUE> {

    private var currentKey = initKey

    private var isLoad:Boolean = false

    private var isAllPageLoaded = false

    private fun isAllPageLoadedStateChange(newState:Boolean) {
        isAllPageLoaded = newState

        if(newState)
            onAllPageLoaded()
    }

    private fun setLoadState(newState:Boolean) {
        if(newState == isLoad) return
        isLoad = newState
        onLoadStateChanged(newState)
    }

    private val mutex:Mutex = Mutex()

    open override suspend fun load() {
        mutex.withLock {
            try {
                if(isLoad || isAllPageLoaded)
                    return

                setLoadState(true)

                val newPageData = onUpdateDataRequest(currentKey,pageSize)
                onNewPageDataLoaded(newPageData)

                val newKey = onNextKey(currentKey,newPageData)
                if(newKey == null) {
                    isAllPageLoadedStateChange(true)
                } else {
                    currentKey = newKey
                }

            }catch (e:Exception) {
                onError(e)
            } finally {
                setLoadState(false)
            }
        }

    }

    open override fun resetPage() {
        currentKey = initKey
        isAllPageLoadedStateChange(false)
        onKeyReset()
    }
}