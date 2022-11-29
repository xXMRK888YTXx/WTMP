package com.xxmrk888ytxx.coredeps

/**
 * [Ru]
 * Этой аннотацией помечается функция, в которой необходимо вынести строки в ресурсы
 * и перевести их.
 * [En]
 * This annotation marks a function in which it is necessary to transfer strings to resources
 * and translate them.
 */
@Target (
    AnnotationTarget.FUNCTION
)
annotation class MustBeLocalization
