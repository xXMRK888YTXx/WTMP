package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "DeviceEvent",
    indices = [
        Index("eventId",unique = true)
    ]
)
internal data class DeviceEventEntity(
    @PrimaryKey(autoGenerate = true) val eventId:Int = 0,
    val time:Long,
    val eventType:Short
)