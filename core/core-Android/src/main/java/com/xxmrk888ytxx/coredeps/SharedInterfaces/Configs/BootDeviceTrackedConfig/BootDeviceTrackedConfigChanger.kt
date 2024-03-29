package com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.BootDeviceTrackedConfig

/**
 * [Ru]
 * Данный интерфейс предназначен для изменения хранящегося в памяти приложения
 * [BootDeviceTrackedConfig]
 */
/**
 * [En]
 * This interface is for changing the stored application
 * [BootDeviceTrackedConfig]
 */
interface BootDeviceTrackedConfigChanger {
    /**
     * [Ru]
     * При изменении данного параметра, все остальные параметры(если не сказано иного), должно
     * быть установлены в состояние false
     */

    /**
     * [En]
     * When changing this parameter, all other parameters (unless otherwise stated) must
     * be set to false
     */
    suspend fun updateIsTracked(state:Boolean)

    suspend fun updateMakePhoto(state: Boolean)

    suspend fun updateNotifyInTelegram(state: Boolean)

    suspend fun updateJoinPhotoToTelegramNotify(state: Boolean)
}