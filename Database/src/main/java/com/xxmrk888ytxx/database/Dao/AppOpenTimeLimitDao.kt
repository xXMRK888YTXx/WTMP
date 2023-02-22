package com.xxmrk888ytxx.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xxmrk888ytxx.database.Entity.AppOpenTimeLimitEntity

@Dao
internal interface AppOpenTimeLimitDao {
    @Query("SELECT * FROM AppOpenTimeLimit WHERE packageName = :packageName")
    fun getTimeLimitForApp(packageName:String) : AppOpenTimeLimitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLimit(appOpenTimeLimitEntity: AppOpenTimeLimitEntity)

    @Query("DELETE FROM AppOpenTimeLimit WHERE packageName = :packageName")
    fun removeLimit(packageName: String)
}