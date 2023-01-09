package com.xxmrk888ytxx.coredeps.SharedInterfaces

import androidx.annotation.IdRes

interface ToastManager {
    fun showToast(text:String)

    fun showToast(@IdRes resId:Int)
}