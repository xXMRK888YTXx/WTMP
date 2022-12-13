package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index("id", unique = true)
    ]
)
internal data class UnlockEventEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val time:Long,
    val unlockEventType:Short
)