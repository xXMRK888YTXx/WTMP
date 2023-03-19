package com.xxmrk888ytxx.coredeps

import com.xxmrk888ytxx.coredeps.SharedInterfaces.Paginator
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * [Ru]
 * Стандарстная реализация пагинатора
 *
 * Принимаемые параметры:
 * @param initKey - начальное значение ключа страницы
 *
 * @param pageSize - размер страницы
 *
 * @param onLoadStateChanged - Лямбда которая оповещает об изменении состоянии загруки данных
 * если возвращает: true - Загрузка в процессе,false - загрузка прекращена. На время загрузки новых
 * данных пагинатор будет игнорировать другие запросы на обновление данных.
 *
 * @param onUpdateDataRequest - Лямбда принимающая ключ страницы для загрузки и её желаемый размер,
 * по этим данным, должна предаставить новую страницу
 *
 * @param onNextKey - Лямбда для получения нового ключа. Принимает в себя текущий ключ и полученые
 * по этому ключу данные. В случае если данная функция возвращает null, считается что больше данных
 * для загрузки нет.
 *
 * @param onError - Лямбда вызываемая, если при загрузки новых данных произошла ошибка.
 *
 * @param onNewPageDataLoaded - Лямбда вызываемая, когда новые данные успешно загружены.
 *
 * @param onKeyReset - Лямбда вызываемая при сбросе текущего ключа через метод [resetPage].
 *
 * @param onAllPageLoaded - Лямбда вызываемая когда всё данные загрущены
 * (т.е лямбда onNextKey вернула null). После вызова данной лямбды все попытки обновить данные
 * будут игнорировать, пока не будет вызван метод [resetPage]
 */

/**
 * [En]
 * Standard implementation of the paginator
 *
 * Accepted parameters:
 * @param initKey is the initial value of the page key
 *
 * @param pageSize is the page size
 *
 * @param onLoadStateChanged is a Lambda that notifies about a change in the state of data loading
 * if it returns: true - Loading is in progress,false - loading is stopped. While the new
 * data is being loaded, the paginator will ignore other data update requests.
 *
 * @param onUpdateDataRequest - Lambda accepting the key of the page to load and its desired size,
 * according to this data, a new page must be submitted
 *
 * @param onNextKey - Lambda to get a new key. Accepts the current key and the received
 * data for this key. If this function returns null, it is assumed that there is more data
 * there is no download.
 *
 * @param onError - Lambda called if an error occurred when loading new data.
 *
 * @param onNewPageDataLoaded - Lambda called when new data is successfully loaded.
 *
 * @param onKeyReset - Lambda called when the current key is reset via the [resetPage] method.
 *
 * @param onAllPageLoaded - Lambda called when all data is loaded
 * (i.e. lambda onNextKey returned null). After calling this lambda, all attempts to update the data
 * will be ignored until the [resetPage] method is called
 */
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