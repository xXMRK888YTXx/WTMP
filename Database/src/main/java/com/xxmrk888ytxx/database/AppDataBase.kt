package com.xxmrk888ytxx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.Dao.AppOpenEventDao
import com.xxmrk888ytxx.database.Dao.RegisteredEventsDao
import com.xxmrk888ytxx.database.Dao.UnlockEventDao
import com.xxmrk888ytxx.database.Entity.AppOpenEventEntity
import com.xxmrk888ytxx.database.Entity.RegisteredEventsEntity
import com.xxmrk888ytxx.database.Entity.UnlockEventEntity

@Database(
    entities = [
        UnlockEventEntity::class,
        AppOpenEventEntity::class,
        RegisteredEventsEntity::class
    ],
    version = 1
)
internal abstract class AppDataBase : RoomDatabase() {
    abstract fun getUnlockEventDao() : UnlockEventDao

    abstract fun getAppOpenEventDao() : AppOpenEventDao

    abstract fun getRegisteredEventsDao() : RegisteredEventsDao
}