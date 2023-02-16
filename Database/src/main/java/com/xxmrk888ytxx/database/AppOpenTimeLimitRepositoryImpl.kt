package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.AppOpenTimeLimitRepository
import com.xxmrk888ytxx.database.DI.DaggerDataBaseComponent
import com.xxmrk888ytxx.database.DI.DataBaseComponent
import com.xxmrk888ytxx.database.Dao.AppOpenTimeLimitDao
import com.xxmrk888ytxx.database.Entity.AppOpenTimeLimitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppOpenTimeLimitRepositoryImpl @Inject constructor(
    private val context:Context
) : AppOpenTimeLimitRepository {

    private val dataBaseComponent: DataBaseComponent by lazy {
        DaggerDataBaseComponent.factory().create(context)
    }

    private val appOpenTimeLimitDao : AppOpenTimeLimitDao by lazy {
        dataBaseComponent.appOpenTimeLimitDao
    }

    private val mutex:Mutex = Mutex()

    override suspend fun getTimeLimitForApp(packageName: String): Pair<String, Long>? {
        mutex.withLock {
            var result:Pair<String,Long>? = null
            withContext(Dispatchers.IO) {
               val resultEntity = appOpenTimeLimitDao.getTimeLimitForApp(packageName)
               if(resultEntity != null) {
                   result = Pair(resultEntity.packageName,resultEntity.timeLimitEnableBeforeTime)
               }
            }

            return result
        }
    }

    override suspend fun addLimit(packageName: String, time: Long) {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                appOpenTimeLimitDao.insertLimit(AppOpenTimeLimitEntity(packageName,time))
            }
        }
    }

    override suspend fun removeLimit(packageName: String) {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                appOpenTimeLimitDao.removeLimit(packageName)
            }
        }
    }
}