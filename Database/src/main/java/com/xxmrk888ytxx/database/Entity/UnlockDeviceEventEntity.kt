package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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