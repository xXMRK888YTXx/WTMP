package com.xxmrk888ytxx.observer.domain.RemoveAppManager

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xxmrk888ytxx.coredeps.SharedInterfaces.RemoveAppManager
import javax.inject.Inject


class RemoveAppManagerImpl @Inject constructor(
    private val context: Context,
) : RemoveAppManager {

    override fun requestRemoveApp() {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(intent)
    }
}