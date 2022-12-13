package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.Entity.UnlockEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface UnlockEventDao {
    @Query("SELECT * FROM UnlockEventEntity")
    fun getAllEvents() : Flow<List<UnlockEventEntity>>

    @Insert
    suspend fun addEvent(event:UnlockEventEntity)

    @Delete
    suspend fun removeEvent(event:UnlockEventEntity)
}