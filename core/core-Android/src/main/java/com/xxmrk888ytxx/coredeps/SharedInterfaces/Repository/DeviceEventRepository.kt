package com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository

import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Интерфейс необходим для хранения и управления хранимыми событиями приложения
 */
/**
 * [Ru]
 * An interface is required to store and manage stored application events
 */
interface DeviceEventRepository {

    /**
     * [Ru]
     * Возвращает всё хранимые события
     */
    /**
     * [Ru]
     * Returns all stored events
     */
    fun getAllEvents() : Flow<List<DeviceEvent>>

    /**
     * [Ru]
     * Возвращает события которые произошли в заданный промежуток времени
     */
    /**
     * [En]
     * Returns events that happened within the specified time period
     */
    fun getEventInTimeSpan(start:Long,end:Long) : Flow<List<DeviceEvent>>

    /**
     * [Ru]
     * Возвращает событие, по его id
     */
    /**
     * [En]
     * Returns the event, by its id
     */
    fun getEvent(eventId: Int) : Flow<DeviceEvent>

    /**
     * [Ru]
     * Добавляет событие, и возвращает его id
     */
    /**
     * [En]
     * Adds an event and returns its id
     */
    suspend fun addEvent(deviceEvent: DeviceEvent) : Int

    /**
     * [Ru]
     * Удаляет событие по его id
     */
    /**
     * [En]
     * Deletes an event by its id
     */
    suspend fun removeEvent(eventId:Int)
}