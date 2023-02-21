package com.xxmrk888ytxx.coredeps.models

/**
 * [Ru]
 * Настройки приложения, которые задают правила хранения отчётов
 * @param maxReportCount - Максимальное количество отчётов, которое может храниться единовременно
 * @param maxReportStorageTime - максимальное время хранения отчёта(в мс)
 */
/**
 * [En]
 * Application settings that set the rules for storing reports
 * @*@param maxReportCount - The maximum number of reports that can be stored at a time
 * @param maxReportStorageTime - maximum report storage time (in ms)
 */
data class StorageConfig(
    val maxReportCount:Int = 0,
    val maxReportStorageTime:Long = 0
)