package com.xxmrk888ytxx.coredeps

import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

class DefaultPaginatorTest {

    private data class TestData(val id:Int)

    class TestException : Exception()


    private suspend fun fakeLoader(page:Int,limit:Int) : List<TestData> {
        return try {
            val all = (1..10)

            val list = all.drop(page * limit).take(limit)

            list.map { TestData(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    @Test
    fun requestPagingDataExpectPaginatorSendActualResult() = runBlocking {
        val limit = 3
        val page0 = fakeLoader(0,limit)
        val page1 = fakeLoader(1,limit)
        val page2 = fakeLoader(2,limit)
        val page3 = fakeLoader(3,limit)

        var currentLoaded:List<TestData>? = null
        var isLoaded = false
        var isAllLoaded = false

        val paginator = DefaultPaginator<Int,List<TestData>>(
            initKey = 0,
            pageSize = limit,
            onLoadStateChanged = {
                isLoaded = it
            },
            onUpdateDataRequest = { page,pageSize:Int ->
                fakeLoader(page,pageSize)
            },
            onNextKey = { currentKey, newData ->
                return@DefaultPaginator if(newData.isEmpty()) null
                else currentKey + 1
            },
            onError = {
                throw it
            },
            onNewPageDataLoaded = { list ->
                currentLoaded = list
            },
            onKeyReset = {
                isAllLoaded = false
            },
            onAllPageLoaded = {
                isAllLoaded = true
            }
        )
        //
        paginator.load()
        while (isLoaded) { delay(1) }
        val page0FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page1FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page2FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page3FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page4FromPaginator = currentLoaded!!

        Assert.assertEquals(page0,page0FromPaginator)
        Assert.assertEquals(page1,page1FromPaginator)
        Assert.assertEquals(page2,page2FromPaginator)
        Assert.assertEquals(page3,page3FromPaginator)
        Assert.assertEquals(emptyList<TestData>(),page4FromPaginator)
        Assert.assertEquals(true,isAllLoaded)
    }

    @Test
    fun requestPagingDataAndResetPaginatorExpectLoadStartWithInitKey() = runBlocking {
        val limit = 3
        val page0 = fakeLoader(0,limit)
        val page1 = fakeLoader(1,limit)
        val page2 = fakeLoader(2,limit)
        val page0AfterReset = fakeLoader(0,limit)
        Assert.assertEquals(page0AfterReset,page0)

        var currentLoaded:List<TestData>? = null
        var isLoaded = false
        var isAllLoaded = false

        val paginator = DefaultPaginator<Int,List<TestData>>(
            initKey = 0,
            pageSize = limit,
            onLoadStateChanged = {
                isLoaded = it
            },
            onUpdateDataRequest = { page,pageSize:Int ->
                fakeLoader(page,pageSize)
            },
            onNextKey = { currentKey, newData ->
                return@DefaultPaginator if(newData.isEmpty()) null
                else currentKey + 1
            },
            onError = {
                throw it
            },
            onNewPageDataLoaded = { list ->
                currentLoaded = list
            },
            onKeyReset = {
                isAllLoaded = false
            },
            onAllPageLoaded = {
                isAllLoaded = true
            }
        )
        //
        paginator.load()
        while (isLoaded) { delay(1) }
        val page0FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page1FromPaginator = currentLoaded!!

        paginator.load()
        while (isLoaded) { delay(1) }
        val page2FromPaginator = currentLoaded!!

        paginator.resetPage()

        paginator.load()
        while (isLoaded) { delay(1) }


        Assert.assertEquals(page0,page0FromPaginator)
        Assert.assertEquals(page1,page1FromPaginator)
        Assert.assertEquals(page2,page2FromPaginator)
        Assert.assertEquals(page0AfterReset,currentLoaded)
    }

    @Test(expected = TestException::class)
    fun requestPagingDataButThrowExceptionInLoadDataExpectExceptionSendInOnErrorLambda() = runBlocking {
        val paginator = DefaultPaginator<Int,List<TestData>>(
            initKey = 0,
            pageSize = 10,
            onLoadStateChanged = {

            },
            onUpdateDataRequest = { page,pageSize:Int ->
                throw java.lang.RuntimeException()
            },
            onNextKey = { currentKey, newData ->
                TODO()
            },
            onError = {
                throw TestException()
            },
            onNewPageDataLoaded = { list ->

            },
            onKeyReset = {

            },
            onAllPageLoaded = {

            }
        )

        paginator.load()
    }
}