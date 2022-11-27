package com.xxmrk888ytxx.coredeps.DepsProvider

import android.content.Context
import com.xxmrk888ytxx.coredeps.Exceptions.ApplicationNotImplementedDepsProvider

@Suppress("UNCHECKED_CAST")
inline fun <reified DEPS : Any> Context.getDepsByApplication() : DEPS {
    if(this is DepsProvider) return this.provide(DEPS::class)
    try {
         return (this.applicationContext as DepsProvider).provide(DEPS::class)
    }catch (_:ClassCastException) {
        throw ApplicationNotImplementedDepsProvider("Application class not implemented DepsProvider")
    }
}