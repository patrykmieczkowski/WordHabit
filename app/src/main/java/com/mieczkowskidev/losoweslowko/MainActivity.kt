package com.mieczkowskidev.losoweslowko

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu


class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "main_channel"
    // notification ID is needed to update or remove the notification
    val GLOBAL_NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()
    }

    private fun createNotification() {

        val pendingIntent = activityPendingIntent()

        val notificationBuilder = createNotificationBuilder(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel()
        }

        showTheNotification(notificationBuilder)

    }

    fun createNotificationBuilder(pendingIntent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("Dog3")
                .setContentText("Pies3")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("This romnatic, i can and the greezly bear he grabs and he can, " +
                                "greezly bear he grabs and he can, but he is am ountain ma" +
                                "greezly bear he grabs and he can, but he is am ountain ma" +
                                "but he is am ountain main :) :) am i am a mountain mn"))
                // set priority support Android 7.1 and lower (8.0+ set it in Notification Channel)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // content intent - intent triggered on user click
                .setContentIntent(pendingIntent)
                // auto cancel true - automatically removes the notification when the users taps it
                .setAutoCancel(true)
    }

    fun showTheNotification(notificationBuilder: NotificationCompat.Builder) {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(GLOBAL_NOTIFICATION_ID, notificationBuilder.build())
    }

    fun activityPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

    /**
     * used on on Android 8.0 (API 26+) and higher to register notification channel in the system
     * by passing an instance of NotificationChannel to createNotificationChannel()
     * because NotificationChannel class is new and not in the support Library
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun registerNotificationChannel() {

        val name = getString(R.string.notification_channel_name)
        val description = getString(R.string.notification_channel_description)
        val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }
}
