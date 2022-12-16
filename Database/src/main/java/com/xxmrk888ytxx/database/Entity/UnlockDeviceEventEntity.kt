package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.xxmrk888ytxx.coredeps.models.DeviceEvent

/**
 * [Ru]
 * Таблица которая является представлением модели [DeviceEvent.AttemptUnlockDevice] для
 * базы данных.
 * @param eventId - id события, который является внешним ключём на базовую таблицу с событиями
 * @param lockEventType - событие блокировки/разблокировки в виде числа
 */
/**
 * [En]
 * A table that is a representation of the [DeviceEvent.AttemptUnlockDevice] model for
 * Database.
 * @param eventId - event id, which is a foreign key to the base table with events
 * @param lockEventType - lock/unlock event as a number
 */
@Entity(
    tableName = "UnlockDeviceEvents",
    indices = [
        Index("eventId", unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = DeviceEventEntity::class,
            parentColumns = ["eventId"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
internal data class UnlockDeviceEventEntity(
    @PrimaryKey val eventId:Int,
    val lockEventType:Short
)