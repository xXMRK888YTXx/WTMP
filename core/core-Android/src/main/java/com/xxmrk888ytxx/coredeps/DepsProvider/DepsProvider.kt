package com.xxmrk888ytxx.coredeps.DepsProvider

import com.xxmrk888ytxx.coredeps.Exceptions.DepsProviderNotFoundDeps
import kotlin.reflect.KClass

/**
 * [Ru]
 * Данный интерфейс предназначен для предоставления зависимости в из модуля [app]
 * в другие модули, не подключая самого модуля [app].
 * Подразумевается, что данный интерфейс реализует класс который наследуется от
 * класса [Application]. В этом случаи для получения необходимой зависимости
 * можно использовать функцию расширения [getDepsByApplication].
 * Если [DepsProvider] не может предоставить зависимость, он должен выбросить исключение
 * @throws DepsProviderNotFoundDeps
 * [En]
 * This interface is intended to provide a dependency in from the [app] module
 * to other modules without including the [app] module itself.
 * It is assumed that this interface implements a class that is inherited from
 * class [Application]. In this case, to obtain the necessary dependence
 * you can use the extension function [getDepsByApplication].
 * If [DepsProvider] cannot provide a dependency, it should throw an exception
 * @throws DepsProviderNotFoundDeps
 */
interface DepsProvider {
    fun <DEPS : Any> provide(classType:KClass<DEPS>) : DEPS
}