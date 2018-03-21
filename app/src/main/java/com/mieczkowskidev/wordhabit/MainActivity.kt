package com.mieczkowskidev.wordhabit

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

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()

        FirebaseReader().readFromFirebase()
    }

    private fun createNotification() {

        val activityStartIntent = getActivityStartIntent()

        val notificationBuilder = NotificationProvider().createNotificationBuilder(this, activityStartIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel()
        }

        showTheNotification(notificationBuilder)

    }

    fun showTheNotification(notificationBuilder: NotificationCompat.Builder) {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NotificationProvider().GLOBAL_NOTIFICATION_ID, notificationBuilder.build())
    }

    fun getActivityStartIntent(): PendingIntent {
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
        val channel = NotificationChannel(NotificationProvider().CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
