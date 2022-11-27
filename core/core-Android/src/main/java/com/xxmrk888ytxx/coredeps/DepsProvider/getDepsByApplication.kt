package com.xxmrk888ytxx.coredeps.DepsProvider

import android.content.Context
import com.xxmrk888ytxx.coredeps.Exceptions.ApplicationNotImplementedDepsProvider
import com.xxmrk888ytxx.coredeps.Exceptions.DepsProviderNotFoundDeps

/**
 * [Ru]
 * Данная функция расширения предназначена для получения необходимой
 * зависимосимости из интерфейса [DepsProvider]
 * который данныя функция пытается получить из класс который наследуется от класса [Application],
 * если данный класс не реализует [DepsProvider], то будет выброшено исключение
 * @throws ApplicationNotImplementedDepsProvider
 * Если [DepsProvider.provide] не может предоставить необходимую зависимость, то будет выброшено
 * исключение
 * @throws DepsProviderNotFoundDeps
 * [En]
 * This extension function is intended to obtain the necessary
 * dependencies from the [DepsProvider] interface
 * which this function is trying to get from the class that is inherited from the [Application] class,
 * if this class does not implement [DepsProvider], an exception will be thrown
 * @throws ApplicationNotImplementedDepsProvider
 * If [DepsProvider.provide] cannot provide the required dependency, it will be thrown
 * exception
 * @throws DepsProviderNotFoundDeps
 */
@Suppress("UNCHECKED_CAST")
@Throws(
    ApplicationNotImplementedDepsProvider::class,
    DepsProviderNotFoundDeps::class
)
inline fun <reified DEPS : Any> Context.getDepsByApplication() : DEPS {
    if(this is DepsProvider) return this.provide(DEPS::class)
    try {
         return (this.applicationContext as DepsProvider).provide(DEPS::class)
    }catch (_:ClassCastException) {
        throw ApplicationNotImplementedDepsProvider("Application class not implemented DepsProvider")
    }
}