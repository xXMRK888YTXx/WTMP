package com.xxmrk888ytxx.observer.domain.NotificationAppManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.xxmrk888ytxx.coredeps.MustBeLocalization
import com.xxmrk888ytxx.observer.MainActivity
import com.xxmrk888ytxx.observer.R
import javax.inject.Inject

class NotificationAppManagerImpl @Inject constructor(
    private val context:Context,
) : NotificationAppManager {

    private val channelId = "AppErrorChannel"

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @MustBeLocalization
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = context.getString(R.string.Application_error_notifications)
            val description = context.getString(R.string.Channel_description)
            val mChannel = NotificationChannel(channelId,channelName,
                NotificationManager.IMPORTANCE_HIGH)
            mChannel.description = description
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun createChannelAndCheckPermission() : Boolean {
        createNotificationChannel()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    @MustBeLocalization
    override fun sendAdminPermissionWithdrawnNotification() {
        if(!createChannelAndCheckPermission()) return
        val intent = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.drawable.ic_error)
            .setContentTitle(context.getString(R.string.Administrator_rights_revoked))
            .setContentText(context.getString(R.string.Without_this_permission_application_not_possible))
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(0,notification)
    }

    @MustBeLocalization
    override fun sendAccessibilityPermissionWithdrawnNotification() {
        if(!createChannelAndCheckPermission()) return
        val intent = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.drawable.ic_error)
            .setContentTitle(context.getString(R.string.Accessibility_rights_revoked))
            .setContentText(context.getString(R.string.Without_this_permission_application_not_possible))
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(1,notification)
    }
}