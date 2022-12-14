package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity

@Dao
internal interface AppOpenEventDao {
    @Insert
    suspend fun addEvent(appOpenEventEntity: AppOpenEventEntity)
}