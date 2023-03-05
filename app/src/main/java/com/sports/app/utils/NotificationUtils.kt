package com.sports.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.sports.app.R

object NotificationUtils {

    fun showNotification(context: Context, title: String?, body: String?, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-sports-app"
        val channelName = "Sports App"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId,
                channelName,
                importance,
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent: PendingIntent? = stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_IMMUTABLE,
        )
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(notificationId, builder.build())
    }
}
