package com.xxmrk888ytxx.database.Entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index("packageName", unique = true)
    ],
    tableName = "AppOpenTimeLimit"
)
internal data class AppOpenTimeLimitEntity (
    @PrimaryKey val packageName:String,
    val timeLimitEnableBeforeTime:Long
)