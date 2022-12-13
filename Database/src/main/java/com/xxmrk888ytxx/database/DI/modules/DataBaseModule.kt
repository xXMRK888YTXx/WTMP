package com.xxmrk888ytxx.database.DI.modules

import android.content.Context
import androidx.room.Room
import com.xxmrk888ytxx.database.AppDataBase
import com.xxmrk888ytxx.database.DI.DataBaseScope
import com.xxmrk888ytxx.database.Dao.AppOpenEventDao
import com.xxmrk888ytxx.database.Dao.RegisteredEventsDao
import com.xxmrk888ytxx.database.Dao.UnlockEventDao
import dagger.Module
import dagger.Provides

@Module
internal class DataBaseModule {
    @Provides
    @DataBaseScope
    fun provideAppDataBase(context: Context) : AppDataBase {
        return Room.databaseBuilder(context,AppDataBase::class.java,"Database.db").build()
    }

    @Provides
    fun provideUnlockEventDao(dataBase: AppDataBase) : UnlockEventDao {
        return dataBase.getUnlockEventDao()
    }

    @Provides
    fun provideAppOpenEventDao(dataBase: AppDataBase) : AppOpenEventDao {
        return dataBase.getAppOpenEventDao()
    }

    @Provides
    fun provideRegisteredEventsDao(dataBase: AppDataBase) : RegisteredEventsDao {
        return dataBase.getRegisteredEventsDao()
    }

}