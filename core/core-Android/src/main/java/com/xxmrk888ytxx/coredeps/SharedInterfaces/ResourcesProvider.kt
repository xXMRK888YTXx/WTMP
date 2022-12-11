package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.annotation.IdRes

interface ResourcesProvider {
    fun getString(@IdRes stringRes: Int) : String
}