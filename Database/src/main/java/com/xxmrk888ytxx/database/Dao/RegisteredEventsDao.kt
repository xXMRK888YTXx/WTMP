package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.Entity.RegisteredEventsEntity

@Dao
internal interface RegisteredEventsDao {
    @Insert
    suspend fun registerEvent(registerInfo: RegisteredEventsEntity)

    @Query("DELETE FROM RegisteredEventsEntity WHERE eventId = :eventId")
    suspend fun removeEvent(eventId:Int)
}