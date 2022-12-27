package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.Entity.TrackedAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackedAppDao {

    @Query("SELECT * FROM TrackedAppTable")
    fun getAllTrackedApp() : Flow<TrackedAppEntity>

    @Insert
    suspend fun addTrackedApp(trackedAppEntity: TrackedAppEntity)

    @Query("DELETE FROM TrackedAppTable WHERE packageName = :packageName")
    suspend fun removeTrackedApp(packageName: String)
}