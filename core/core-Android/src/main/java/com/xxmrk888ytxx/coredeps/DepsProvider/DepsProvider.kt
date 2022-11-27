package com.xxmrk888ytxx.coredeps.DepsProvider

import kotlin.reflect.KClass

interface DepsProvider {
    fun <DEPS : Any> provide(classType:KClass<DEPS>) : DEPS
}