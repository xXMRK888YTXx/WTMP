package com.xxmrk888ytxx.observer.domain.TimeLimitManagers

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.xxmrk888ytxx.coredeps.SharedInterfaces.TimeOperationLimitManager.TimeOperationLimitManager
import com.xxmrk888ytxx.observer.domain.SettingsAppManager.SettingsAppManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SucceededUnlockTimeOperationLimitManagerImpl @Inject constructor(
    private val settingsAppManager: SettingsAppManager
) : TimeOperationLimitManager<Nothing> {
    private val limitEnableBeforeTimeKey = longPreferencesKey("SucceededUnlock/limitEnableBeforeTime")
    private val lastLimitTime = intPreferencesKey("SucceededUnlock/lastLimitTime")

    /**
     * [Ru]
     * Метод для установки лимита
     * @param installedPeriodLimit - время установки лимита в мс
     * @param otherData - не используется
     *
     * При вызове метода будет записано время окончания лимита,
     * выглядит это так - Текущее время + время на которое нужно установить лимит
     *
     * При попытки установить лимит с временем(@param installedPeriodLimit)
     *  которое в данный момент уже установлено (isLimitEnable(...) == true)
     *  нечего не произойдет
     *
     * Например:
     * Время лимита - 1000мс
     * 0(текущее время):enableLimit(1000) - лимит установлен
     * 999:enableLimit(1000) - нечего не произойдет
     * 1001: enableLimit(1000) - установлен новый лимит
     */
    /**
     * [En]
     * Method to set limit
     * @param installedPeriodLimit - limit setting time in ms
     * @param otherData - not used
     *
     * When calling the method, the limit end time will be recorded,
     * it looks like this - Current time + time for which you need to set a limit
     *
     * When trying to set a limit with time(@param installedPeriodLimit)
     * which is currently already set (isLimitEnable(...) == true)
     * nothing will happen
     *
     * For example:
     * Limit time - 1000ms
     * 0(current time):enableLimit(1000) - the limit is set
     * 999:enableLimit(1000) - nothing will happen
     * 1001: enableLimit(1000) - new limit set
     */
    override suspend fun enableLimit(installedPeriodLimit: Int, otherData: Nothing?) {
        if(isLimitEnable(installedPeriodLimit,otherData))
            return

        val newLimitTime = System.currentTimeMillis() + installedPeriodLimit

        withContext(Dispatchers.IO) {
            settingsAppManager.writeProperty(limitEnableBeforeTimeKey,newLimitTime)
            settingsAppManager.writeProperty(lastLimitTime,installedPeriodLimit)
        }
    }

    /**
     * [Ru]
     * Метод для проверки есть ли установленный лимит в данный момент
     * @param installedPeriodLimit - время установки лимита в мс
     * @param otherData - не используется
     *
     * Метод вернёт true если -  текущее время < время окончания лимита
     * При этом @param installedPeriodLimit должен быть таким же как и при установке лимита.
     * Например
     * Время лимита 1000мс
     * 1(текущее время): isLimitEnable(1000) == true
     * 1001: isLimitEnable(1000) == false
     *
     *
     * Если @param installedPeriodLimit отличается от того что был при установке лимита,
     * лимит отменяется и метод возвращает false
     * Например
     * Время лимита 1000мс
     * 1: isLimitEnable(1000) == true
     * 500: isLimitEnable(100) == false
     * 501: isLimitEnable(1000) == false
     */
    /**
     * [En]
     * Method for checking if there is currently a set limit
     * @param installedPeriodLimit - limit setting time in ms
     * @param otherData - not used
     *
     * The method will return true if - current time < limit end time
     * In this case, @param installedPeriodLimit must be the same as when setting the limit.
     * For example
     * Limit time 1000ms
     * 1(current time): isLimitEnable(1000) == true
     * 1001: isLimitEnable(1000) == false
     *
     *
     * If @param installedPeriodLimit is different from what it was when setting the limit,
     * the limit is canceled and the method returns false
     * For example
     * Limit time 1000ms
     *1: isLimitEnable(1000) == true
     * 500: isLimitEnable(100) == false
     * 501: isLimitEnable(1000) == false
     */
    override suspend fun isLimitEnable(installedPeriodLimit: Int, otherData: Nothing?): Boolean {
        if(installedPeriodLimit == 0) return false
        val limitEnableBeforeTime = settingsAppManager.getProperty(limitEnableBeforeTimeKey,0)
        val lastLimitTime = settingsAppManager.getProperty(lastLimitTime,0)

        if(lastLimitTime.first() != installedPeriodLimit) {
            disableLimit()
            return false
        }


        val limitState = limitEnableBeforeTime.first() > System.currentTimeMillis()

        //Resetting obsolete values
        if(!limitState&&(limitEnableBeforeTime.first() != 0L||lastLimitTime.first() != 0)) {
            disableLimit()
        }

        return limitState
    }

    /**
     * [Ru]
     * Отменят установленный лимит
     */
    /**
     * [En]
     * Will cancel the set limit
     */
    override suspend fun disableLimit(otherData: Nothing?) {
        withContext(Dispatchers.IO) {
            settingsAppManager.writeProperty(limitEnableBeforeTimeKey,0)
            settingsAppManager.writeProperty(limitEnableBeforeTimeKey,0)
        }
    }

}