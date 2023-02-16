package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.AppOpenTimeLimitRepository
import com.xxmrk888ytxx.coredeps.models.AppOpenTimeLimitModel
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

    override suspend fun getTimeLimitForApp(packageName: String): AppOpenTimeLimitModel? {
        mutex.withLock {
            var result:AppOpenTimeLimitModel? = null
            withContext(Dispatchers.IO) {
               val resultEntity = appOpenTimeLimitDao.getTimeLimitForApp(packageName)
               if(resultEntity != null) {
                   result = AppOpenTimeLimitModel(
                       packageName = resultEntity.packageName,
                       endLimitTime = resultEntity.timeLimitEnableBeforeTime,
                       timeSetupLimit = resultEntity.timeSetupLimit
                   )
               }
            }

            return result
        }
    }

    override suspend fun addLimit(appOpenTimeLimitModel:AppOpenTimeLimitModel) {
        mutex.withLock {
            withContext(Dispatchers.IO) {
                appOpenTimeLimitDao.insertLimit(
                    AppOpenTimeLimitEntity(
                        packageName = appOpenTimeLimitModel.packageName,
                        timeLimitEnableBeforeTime = appOpenTimeLimitModel.endLimitTime,
                        timeSetupLimit = appOpenTimeLimitModel.timeSetupLimit
                    )
                )
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