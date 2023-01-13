package com.xxmrk888ytxx.bootreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

/**
 * [Ru]
 * [BroadcastReceiver] который уведомляет о загрузке устройства
 * по средствам callback [BootCallback], который получается из [DepsProvider]
 */
internal class BootReceiver : BroadcastReceiver() {

    private val Context.bootCallback:BootCallback
        get() = this.getDepsByApplication()

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
            context.bootCallback.onBootCompleted()
        }
    }

}