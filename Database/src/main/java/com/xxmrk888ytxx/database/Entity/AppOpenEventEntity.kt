package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "AppOpenEvents",
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
internal data class AppOpenEventEntity(
    @PrimaryKey val eventId:Int,
    val packageName:String,
    val appName:String?
)