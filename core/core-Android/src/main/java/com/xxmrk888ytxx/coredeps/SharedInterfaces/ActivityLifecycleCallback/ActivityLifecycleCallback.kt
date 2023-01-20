package com.xxmrk888ytxx.coredeps.SharedInterfaces.ActivityLifecycleCallback

import android.app.Activity

/**
 * [Ru]
 * Данный интерфейс представляет собой callback для получения состояния [Activity] в которой
 * работает приложение
 */

/**
 * [En]
 * This interface is a callback for getting the [Activity] state in which
 * application running
 */
interface ActivityLifecycleCallback {
    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity].
     * Если вы сохраняете ссылку на [Activity], не забудьте очистить её в методе
     * [onDestroy]
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     * If you keep a reference to [Activity], don't forget to clear it in the method
     * [onDestroy]
     */
    fun onCreate(activity: Activity) {}

    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity]
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     */
    fun onStart() {}

    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity]
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     */
    fun onResume() {}

    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity]
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     */
    fun onPause() {}

    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity]
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     */
    fun onStop() {}

    /**
     * [Ru]
     * Данный метод вызывается при вызове соответствующего метода у [Activity]
     * В данном методе обязательно необходимо отчистить ссылку на [Activity],
     * если вы хранить таковую
     */
    /**
     * [En]
     * This method is called when the corresponding method is called on [Activity]
     * In this method, it is necessary to clear the link to [Activity],
     * if you keep one
     */
    fun onDestroy() {}

    /**
     * [Ru]
     * Данный метод будет вызван как только callback будет зарегистрирован
     * Если вы сохраняете ссылку на [Activity], не забудьте очистить её в методе
     * [onDestroy]
     */
    /**
     * [En]
     * This method will be called as soon as the callback is registered
     * If you keep a reference to [Activity], don't forget to clear it in the method
     * [onDestroy]
     */
    fun onRegister(activity:Activity) {}
}