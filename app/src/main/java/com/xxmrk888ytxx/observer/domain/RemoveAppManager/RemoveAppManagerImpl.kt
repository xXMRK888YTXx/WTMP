package com.xxmrk888ytxx.observer.domain.RemoveAppManager

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xxmrk888ytxx.adminreceiver.AdminReceiver
import com.xxmrk888ytxx.coredeps.ApplicationScope
import com.xxmrk888ytxx.coredeps.SharedInterfaces.Configs.AppState.AppStateChanger
import com.xxmrk888ytxx.coredeps.SharedInterfaces.RemoveAppManager
import kotlinx.coroutines.launch
import javax.inject.Inject


class RemoveAppManagerImpl @Inject constructor(
    private val context: Context,
    private val appStateChanger: AppStateChanger
) : RemoveAppManager {

    override fun requestRemoveApp() {
        ApplicationScope.launch {
            appStateChanger.updateAppState(false)

            val disableAdminIntent = Intent(context,AdminReceiver::class.java)
            disableAdminIntent.action = AdminReceiver.disableAdminAction
            context.sendBroadcast(disableAdminIntent)

            val intent = Intent(Intent.ACTION_DELETE)
            intent.data = Uri.parse("package:${context.packageName}")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}