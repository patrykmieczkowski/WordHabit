package com.mieczkowskidev.wordhabit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

/**
 * Created by Patryk Mieczkowski on 19.03.2018
 */
class NotificationProvider {

    val CHANNEL_ID = "main_channel"
    // notification ID is needed to update or remove the notification
    val GLOBAL_NOTIFICATION_ID = 1

    fun createNotificationBuilder(context: Context, onClickIntent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("Dog3")
                .setContentText("Pies3")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                                "Aliquam eget mi pellentesque, ullamcorper dolor eu, sollicitudin ex. " +
                                "Ut ac velit venenatis, placerat magna in, consequat quam. " +
                                "Morbi nec eros et justo malesuada vestibulum. Curabitur efficitur sapien " +
                                "nec nunc porta, ac venenatis libero viverra. Aliquam ac enim ac augue molestie " +
                                "varius quis id felis. Suspendisse id suscipit arcu. Vestibulum vel rutrum tellus."))
                // set priority support Android 7.1 and lower (8.0+ set it in Notification Channel)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // content intent - intent triggered on user click
                .setContentIntent(onClickIntent)
                // auto cancel true - automatically removes the notification when the users taps it
                .setAutoCancel(true)
                // depending on category system may determine whether to disturb the user or not (alarm, reminder)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                // lock screen visibility (public, secret and default private)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        // when true the notification interups (sound, vibration) only for the first time, not for updates
//                .setOnlyAlertOnce(true)
        // system cancel the notification after specified duration elapses
//                .setTimeoutAfter(2000)

    }

    fun createNotification(context: Context) {

        val activityStartIntent = getActivityStartIntent(context)

        val notificationBuilder = NotificationProvider().createNotificationBuilder(context, activityStartIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel(context)
        }

        showTheNotification(notificationBuilder, context)

    }

    fun showTheNotification(notificationBuilder: NotificationCompat.Builder, context: Context) {
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NotificationProvider().GLOBAL_NOTIFICATION_ID, notificationBuilder.build())
    }

    fun getActivityStartIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    /**
     * used on on Android 8.0 (API 26+) and higher to register notification channel in the system
     * by passing an instance of NotificationChannel to createNotificationChannel()
     * because NotificationChannel class is new and not in the support Library
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun registerNotificationChannel(context: Context) {

        val name = context.getString(R.string.notification_channel_name)
        val description = context.getString(R.string.notification_channel_description)
        val channel = NotificationChannel(NotificationProvider().CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}