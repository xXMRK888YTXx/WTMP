package com.xxmrk888ytxx.observer.domain.BootCallback

import com.xxmrk888ytxx.androidextension.LogcatExtension.logcatMessageD
import com.xxmrk888ytxx.bootreceiver.BootCallback
import javax.inject.Inject

internal class BootCallbackImpl @Inject constructor(

) : BootCallback {

    override fun onBootCompleted() {
        logcatMessageD("Boot finish")
    }

}