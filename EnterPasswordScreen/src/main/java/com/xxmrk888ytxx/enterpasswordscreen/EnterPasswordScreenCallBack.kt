package com.xxmrk888ytxx.enterpasswordscreen

/**
 * [Ru]
 * Данный интерфейс устанавливает действия на [EnterPasswordScreen]
 */

/**
 * [En]
 * This interface sets actions to [EnterPasswordScreen]
 */
interface EnterPasswordScreenCallBack {
    /**
     * [Ru]
     * Данный метод будет вызван, когда пользователь вводит цифру пароля
     */

    /**
     * [En]
     * This method will be called when the user enters a password number
     */
    fun onInputNumber(number:Int)

    /**
     * [Ru]
     * Данный метод будет вызван, когда пользователь захочет
     * удалить последнею цифру введённого пароля
     */

    /**
     * [En]
     * This method will be called when the user wants to
     * delete the last digit of the entered password
     */
    fun onClearNumber()

    /**
     * [Ru]
     * Данный метод будет вызван, когда пользователь захочет
     * удалить всё цифры введённого пароля
     */

    /**
     * [En]
     * This method will be called when the user wants to
     * remove all digits of the entered password
     */
    fun onClearAll()

    /**
     * [Ru]
     * Данный метод будет вызван, когда пользователь захочет авторизоваться через отпечаток пальца
     */

    /**
     * [En]
     * This method will be called when the user wants to log in with a fingerprint
     */
    fun onSendFingerPrintRequest()

    /**
     * [Ru]
     * Размер вводимого пароля
     */
    /**
     * [En]
     * The size of the entered password
     */
    val passwordSize: Int

    /**
     * [Ru]
     * Устанавливает разрешена ли авторизация по отпечатку пальца
     */

    /**
     * [En]
     * Sets whether fingerprint authorization is allowed
     */
    val enableFingerPrintAuthorization : Boolean
}