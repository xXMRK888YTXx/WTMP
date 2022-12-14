package com.xxmrk888ytxx.database.Dao

import androidx.room.*
import com.xxmrk888ytxx.database.Entity.DeviceEventEntity
import com.xxmrk888ytxx.database.models.DeviceEventModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DeviceEventDao {
    @Query("SELECT * FROM DeviceEvent")
    @Transaction
    fun getAllEvents() : Flow<List<DeviceEventModel>>

    @Insert
    suspend fun addEvent(deviceEventEntity: DeviceEventEntity)

    @Query("DELETE FROM DeviceEvent WHERE eventId = :eventId")
    suspend fun removeEvent(eventId:Int)

    @Query("SELECT MAX(eventId) FROM DeviceEvent")
    suspend fun getLastEventId() : Int
}