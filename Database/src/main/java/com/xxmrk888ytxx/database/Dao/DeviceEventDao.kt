package com.xxmrk888ytxx.database.Dao

import androidx.room.*
import com.xxmrk888ytxx.database.Entity.DeviceEventEntity
import com.xxmrk888ytxx.database.models.DeviceEventModel
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DeviceEventDao {

    @Query("SELECT * FROM DeviceEvent ORDER BY time DESC")
    @Transaction
    fun getAllEvents() : Flow<List<DeviceEventModel>>

    @Query("SELECT * FROM DeviceEvent WHERE time BETWEEN :start AND :end ORDER BY time DESC")
    @Transaction
    fun getEventInTimeSpan(start:Long,end:Long) : Flow<List<DeviceEventModel>>

    @Query("SELECT * FROM DeviceEvent ORDER BY time DESC LIMIT :limit OFFSET :offset")
    @Transaction
    suspend fun getPagingEvent(offset:Int,limit:Int) : List<DeviceEventModel>

    @Query("SELECT * FROM DeviceEvent WHERE eventId = :eventId")
    @Transaction
    fun getEvent(eventId: Int) : Flow<DeviceEventModel>

    @Insert
    suspend fun addEvent(deviceEventEntity: DeviceEventEntity)

    @Query("DELETE FROM DeviceEvent WHERE eventId = :eventId")
    suspend fun removeEvent(eventId:Int)

    @Query("SELECT MAX(eventId) FROM DeviceEvent")
    suspend fun getLastEventId() : Int

}