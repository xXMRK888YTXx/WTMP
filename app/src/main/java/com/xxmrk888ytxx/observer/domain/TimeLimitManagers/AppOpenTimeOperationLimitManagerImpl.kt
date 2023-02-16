package com.xxmrk888ytxx.observer.domain.TimeLimitManagers

import androidx.datastore.preferences.core.intPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Repository.AppOpenTimeLimitRepository
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.coredeps.models.AppOpenTimeLimitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AppOpenTimeOperationLimitManagerImpl @Inject constructor(
    private val appOpenTimeLimitRepository: AppOpenTimeLimitRepository
) : TimeOperationLimitManager<String> {

    private val lastLimitTimeKey = intPreferencesKey("AppOpen/lastLimitTime")

    override suspend fun enableLimit(installedPeriodLimit: Int, otherData: String?) {
        val packageName = otherData ?: return
        withContext(Dispatchers.IO) {
            val newLimitTime = System.currentTimeMillis() + installedPeriodLimit

            appOpenTimeLimitRepository.addLimit(
                AppOpenTimeLimitModel(packageName,newLimitTime,installedPeriodLimit)
            )
        }
    }

    override suspend fun isLimitEnable(installedPeriodLimit: Int, otherData: String?): Boolean {
        if(installedPeriodLimit == 0) return false
        val packageName = otherData ?: return false

        val timeLimit = appOpenTimeLimitRepository.getTimeLimitForApp(packageName)
            ?: return false

        if(timeLimit.timeSetupLimit != installedPeriodLimit) {
            disableLimit(packageName)
            return false
        }

        val limitState = timeLimit.endLimitTime > System.currentTimeMillis()

        //Resetting obsolete values
        if (!limitState) {
            disableLimit(packageName)
        }

        return limitState
    }

    override suspend fun disableLimit(otherData: String?) {
        withContext(Dispatchers.IO) {
            val packageName = otherData ?: return@withContext

            appOpenTimeLimitRepository.removeLimit(packageName)
        }
    }


}