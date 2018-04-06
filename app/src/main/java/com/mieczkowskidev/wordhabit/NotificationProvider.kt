package com.mieczkowskidev.wordhabit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Patryk Mieczkowski on 19.03.2018
 */
class NotificationProvider {

    companion object {
        private val TAG = NotificationProvider::class.java.simpleName
    }


    private val CHANNEL_ID = "main_channel"
    // notification ID is needed to update or remove the notification
    private val GLOBAL_NOTIFICATION_ID = 1

    fun createNotificationAndReceiver(appContext: Context, myNotification: MyNotification) {

        Log.d(TAG, "registerBroadcastReceiver() for com.mieczkowskidev.wordhabit.MY_DATA")

//
//        try {
//            if (App.receiver != null) {
//                appContext.unregisterReceiver(App.receiver)
//            }
//        } catch (e: IllegalArgumentException) {
//            Log.i(TAG, "my broadcast receiver is already unregistered")
//        }

        if (!App.isRegistered) {
            Log.d(TAG, "registering receiver MY_DATA")
            val filter = IntentFilter()
            filter.addAction("com.mieczkowskidev.wordhabit.MY_DATA")
            appContext.registerReceiver(App.receiver, filter)
            App.isRegistered = true
        }


        createNotification(appContext, myNotification)
    }

    fun createNotification(appContext: Context, myNotification: MyNotification) {

        val activityStartIntent = getActivityStartIntent(appContext, myNotification)
        val translatePrimPendingIntent = getTranslatePrimPendingIntent(appContext, myNotification)
        val translateSeconPendingIntent = getTranslateSeconPendingIntent(appContext, myNotification)

        val notificationBuilder = notificationBuilder(appContext, activityStartIntent, translatePrimPendingIntent, translateSeconPendingIntent, myNotification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel(appContext)
        }

        showTheNotification(appContext, notificationBuilder)

        if (myNotification.image != null) {
            downloadNotificationImage(appContext, notificationBuilder, myNotification.image!!)
        }

    }

    private fun notificationBuilder(appContext: Context, onClickIntent: PendingIntent, translatePrimPendingIntent: PendingIntent,
                                    translateSeconPendingIntent: PendingIntent,
                                    myNotification: MyNotification): NotificationCompat.Builder {

        Log.d(TAG, "notificationBuilder() translation ${myNotification.translateType} for $myNotification")

        var builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
//                .setStyle(NotificationCompat.BigTextStyle()
//                        .bigText("Lorem ipsum dolor sit amet, consectr."))
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


        if (myNotification.translateType == "primary") {
            builder.setContentTitle(myNotification.primaryLangWord)
                    .setContentText(myNotification.primaryLangDescription)
                    .addAction(android.R.drawable.ic_menu_camera, "TRANSLATION", translateSeconPendingIntent)

        } else {
            builder.setContentTitle(myNotification.secondaryLangWord)
                    .setContentText(myNotification.secondaryLangDescription)
                    .addAction(android.R.drawable.ic_menu_camera, "ORIGINAL", translatePrimPendingIntent)
        }

        return builder

    }

    private fun getActivityStartIntent(appContext: Context, myNotification: MyNotification): PendingIntent {
        val intent = Intent(appContext, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra("myNotification", myNotification)
        return PendingIntent.getActivity(appContext, 0, intent, 0)
    }

    private fun getTranslatePrimPendingIntent(appContext: Context, myNotification: MyNotification): PendingIntent {

        myNotification.translateType = "secondary"
        val intent = Intent()
        intent.action = "com.mieczkowskidev.wordhabit.MY_DATA"
        intent.putExtra("myNotification", myNotification)
        return PendingIntent.getBroadcast(appContext, 0, intent, 0)
    }

    private fun getTranslateSeconPendingIntent(appContext: Context, myNotification: MyNotification): PendingIntent {

        myNotification.translateType = "primary"
        val intent = Intent()
        intent.action = "com.mieczkowskidev.wordhabit.MY_DATA"
        intent.putExtra("myNotification", myNotification)
        return PendingIntent.getBroadcast(appContext, 0, intent, 0)
    }

    /**
     * used on on Android 8.0 (API 26+) and higher to register notification channel in the system
     * by passing an instance of NotificationChannel to createNotificationChannel()
     * because NotificationChannel class is new and not in the support Library
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerNotificationChannel(appContext: Context) {

        val name = appContext.getString(R.string.notification_channel_name)
        val description = appContext.getString(R.string.notification_channel_description)
        val channel = NotificationChannel(NotificationProvider().CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = description

        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun downloadNotificationImage(appContext: Context, notificationBuilder: NotificationCompat.Builder, imageUrl: String) {
        Log.d(TAG, "downloading notification image $imageUrl")

        MyImageManager().getBitmapSingle(appContext, imageUrl)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    notificationBuilder.setSound(null)
                    notificationBuilder.setLargeIcon(it)
//                    notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
//                            .bigPicture(it)
//                            .bigLargeIcon(null))
                    showTheNotification(appContext, notificationBuilder)
                }, { Log.e(TAG, "error while downloading image", it) })
    }

    private fun showTheNotification(context: Context, notificationBuilder: NotificationCompat.Builder) {
        Log.d(TAG, "showTheNotification()")
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NotificationProvider().GLOBAL_NOTIFICATION_ID, notificationBuilder.build())
    }
}