package com.xxmrk888ytxx.observer.domain.ToastManager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.xxmrk888ytxx.coredeps.SharedInterfaces.ToastManager
import javax.inject.Inject

internal class ToastManagerImpl @Inject constructor(
    private val context: Context
) : ToastManager {

    private val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }

    override fun showToast(text: String) {
        mainThreadHandler.post {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ResourceType")
    override fun showToast(resId: Int) {
        mainThreadHandler.post {
            val text = context.getString(resId)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

}