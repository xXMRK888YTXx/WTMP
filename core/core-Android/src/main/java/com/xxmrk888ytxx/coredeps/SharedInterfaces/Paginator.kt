package com.xxmrk888ytxx.coredeps.SharedInterfaces

/**
 * [Ru]
 * Интрерфейс для пагинатора данных принимает два обобщения
 *
 * KEY - ключ странцы
 *
 * VALUE - Возвращаемый тип
 */

/**
 * [En]
 * * The interface for the data paginator accepts two generalizations
 *
 * KEY - the key of the stranger
 *
 * VALUE - Return type
 */
interface Paginator<KEY,VALUE> {

    suspend fun load()

    fun resetPage()
}