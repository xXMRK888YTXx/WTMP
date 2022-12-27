package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TrackedAppTable",
    indices = [
        Index("id", unique = true),
        Index("packageName", unique = true)
    ]
)
data class TrackedAppEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val packageName:String
)