package com.xxmrk888ytxx.coredeps.SharedInterfaces.AppPassword

/**
 * [Ru]
 * Интерфейс для установки/удаления пароля для входа в приложение
 */

/**
 * [En]
 * Interface to set/remove the password to enter the application
 */
interface AppPasswordChanger {

    /**
     * [Ru]
     * Данный метод устанавливает пароль приложения
     * @param password - пароль, который будет установлен как пароль приложения
     *
     * @throws IllegalStateException - выбрасывает исключение, если пароль уже установлен
     */

    /**
     * [En]
     * This method sets the application password
     * @param password - password to be set as application password
     *
     * @throws IllegalStateException - throws an exception if the password is already set
     */
    suspend fun setupAppPassword(password:String)

    /**
     * [Ru]
     * Удаляет пароль приложения
     * @param currentPassword - текущий пароль приложения
     *
     * @throws IllegalArgumentException - выбрасывает исключение, если передаваемый пароль, не является
     * паролем приложения
     * @throws IllegalStateException - выбрасывает исключение, если пароль не установлен
     */

    /**
     * [En]
     * Removes app password
     * @param currentPassword - current application password
     *
     * @throws IllegalArgumentException - throws an exception if the passed password is not
     * application password
     * @throws IllegalStateException - throws an exception if no password is set
     */
    suspend fun removePassword(currentPassword:String)

    /**
     * [Ru]
     * Устанавливает разрешение на использование биометрии для разблокировки приложения
     */

    /**
     * [En]
     * Sets permission to use biometrics to unlock the app
     */
    suspend fun updateFingerPrintAuthorizationState(state:Boolean)
}