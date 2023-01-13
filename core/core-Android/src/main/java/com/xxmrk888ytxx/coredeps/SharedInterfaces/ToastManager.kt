package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.annotation.IdRes

/**
 * [Ru]
 * Интерфейс для показа Toast сообщений
 */
/**
 * [En]
 * Interface to display Toast messages
 */
interface ToastManager {
    /**
     * [Ru]
     * Показывает Toast сообщение с переданным текстом
     */
    /**
     * [En]
     * Shows Toast message with passed text
     */
    fun showToast(text:String)

    /**
     * [Ru]
     * Показывает Toast сообщение с переданным текстом который находятся в ресурсах
     */
    /**
     * [En]
     * Shows Toast message with passed text which is in resources
     */
    fun showToast(@IdRes resId:Int)
}