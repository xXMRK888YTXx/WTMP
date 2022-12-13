package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AppOpenEventDao {
    @Query("SELECT * FROM AppOpenEventEntity")
    fun getAllEvents() : Flow<List<AppOpenEventEntity>>

    @Insert
    suspend fun addEvent(event: AppOpenEventEntity)

    @Delete
    suspend fun removeEvent(event: AppOpenEventEntity)
}