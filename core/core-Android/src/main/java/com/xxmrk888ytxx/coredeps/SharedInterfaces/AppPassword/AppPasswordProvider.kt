package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Интерфейс для проверки пароля приложения и параметров входа в приложение
 */

/**
 * [En]
 * Interface to check app password and app login options
 */
interface AppPasswordProvider {

    /**
     * [Ru]
     * Данный метод проверяет, является ли передаваемый пароль, паролем приложения
     * @param checkingPassword - проверяемый пароль
     *
     * @throws IllegalStateException - выбрасывает исключение, если пароль не установлен
     */

    /**
     * [En]
     * This method checks if the transmitted password is the application password
     * @param checkingPassword - password to be checked
     *
     * @throws IllegalStateException - throws an exception if no password is set
     */
    suspend fun isAppPassword(checkingPassword:String) : Boolean

    /**
     * [Ru]
     * Данный метод, проверяет установлен ли пароль приложения
     */

    /**
     * [En]
     * This method checks if the application password is set
     */
    fun isPasswordSetupFlow() : Flow<Boolean>

    /**
     * [Ru]
     * Данный метод, проверяет разрешен ли вход по биометрическим данным
     */
    /**
     * [En]
     * This method checks if biometric login is allowed
     */
    fun isFingerPrintAuthorizationEnabled() : Flow<Boolean>

}