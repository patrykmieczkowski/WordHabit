package com.mieczkowskidev.losoweslowko

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.view.Menu
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "main_channel"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()
    }

    private fun createNotification() {
        var notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("Dog")
                .setContentText("Pies")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("This romnatic, i can and the greezly bear he grabs and he can, " +
                                "greezly bear he grabs and he can, but he is am ountain ma" +
                                "greezly bear he grabs and he can, but he is am ountain ma" +
                                "but he is am ountain main :) :) am i am a mountain mn"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel()
        }

        var notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notificationBuilder.build())
    }

    /**
     * used on on Android 8.0 (API 26+) and higher to register notification channel in the system
     * by passing an instance of NotificationChannel to createNotificationChannel()
     * because NotificationChannel class is new and not in the support Library
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerNotificationChannel() {

        val name = getString(R.string.notification_channel_name)
        val description = getString(R.string.notification_channel_description)
        val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
    }
}

override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    return super.onCreateOptionsMenu(menu)
}
}
