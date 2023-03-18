package com.xxmrk888ytxx.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * [Ru]
 * Базовая таблица событий.
 * @param eventId - id события
 * @param time - время события
 * @param eventType - тип события, представленный в виде числа
 * Все типы событий должны иметь внешний ключ на поле eventId
 * При добавлении события в данную таблицу, одновременно необходимо добавлять событие в другую
 * таблицу с внешним ключом на эту таблицу, в зависимости от типа события.
 * При удалении события из этой таблицы, они должны удаляться и из других таблиц
 */

/**
 * [Eu]
 * Basic table of events.
 * @param eventId - event id
 * @param time - event time
 * @param eventType - event type, represented as a number
 * All event types must have a foreign key on the eventId field
 * When adding an event to this table, you must simultaneously add an event to another
 * a table with a foreign key to this table, depending on the event type.
 * When deleting events from this table, they must be deleted from other tables as well
 */
@Entity(
    tableName = "DeviceEvent",
    indices = [
        Index("eventId",unique = true)
    ]
)
internal data class DeviceEventEntity(
    @PrimaryKey(autoGenerate = true) val eventId:Int = 0,
    @ColumnInfo("time") val time:Long,
    val eventType:Short
)