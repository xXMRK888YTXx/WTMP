package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.annotation.IdRes

/**
 * [Ru]
 * Класс реализующий этот интерфейс должен предоставлять ресурс
 * по его resId
 */
/**
 * [En]
 * A class that implements this interface must provide a resource
 * by its resId
 */
interface ResourcesProvider {
    fun getString(@IdRes stringRes: Int) : String
}