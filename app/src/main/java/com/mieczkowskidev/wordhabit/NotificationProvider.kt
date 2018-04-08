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
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.model.TranslateType
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Patryk Mieczkowski on 19.03.2018
 */
class NotificationProvider {

    companion object {
        val TAG = NotificationProvider::class.java.simpleName
    }

    private val CHANNEL_ID = "main_channel"
    // notification ID is needed to update or remove the notification
    private val GLOBAL_NOTIFICATION_ID = 1

    fun createNotificationAndReceiver(appContext: Context, myNotification: MyNotification) {

        if (!App.isRegistered) {
            registerBroadcastReceiver(appContext)
        }

        createNotification(appContext, myNotification)
    }

    private fun registerBroadcastReceiver(appContext: Context) {
        Log.d(TAG, "registerBroadcastReceiver() for ${NotificationConfig.NOTIFICATION_INTENT_ACTION}")
        val filter = IntentFilter()
        filter.addAction(NotificationConfig.NOTIFICATION_INTENT_ACTION)
        appContext.registerReceiver(App.receiver, filter)
        App.isRegistered = true
    }

    fun createNotification(appContext: Context, myNotification: MyNotification) {

        val activityStartIntent = getActivityStartIntent(appContext, myNotification)
        val translatePendingIntent = getTranslatePendingIntent(appContext, myNotification)

        val notificationBuilder = notificationBuilder(appContext, activityStartIntent, myNotification, translatePendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerNotificationChannel(appContext)
        }

        showTheNotification(appContext, notificationBuilder)

        if (myNotification.image != null && myNotification.image != "") {
            downloadNotificationImage(appContext, notificationBuilder, myNotification.image!!)
        }

    }

    private fun notificationBuilder(appContext: Context, onClickIntent: PendingIntent,
                                    myNotification: MyNotification,
                                    translatePendingIntent: PendingIntent): NotificationCompat.Builder {

        Log.d(TAG, "notificationBuilder() for $myNotification")

        val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
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
        // when true the notification interrupts (sound, vibration) only for the first time, not for updates
//                .setOnlyAlertOnce(true)
        // system cancel the notification after specified duration elapses
//                .setTimeoutAfter(2000)


        if (myNotification.translateType == TranslateType.PRIMARY) {
            Log.d(TAG, "case for primary language, translating to secondary")
            App.state = TranslateType.PRIMARY

            builder.setContentTitle(myNotification.primaryLangWord)
                    .setContentText(myNotification.primaryLangDescription)
                    .addAction(R.drawable.ic_translate_black_24dp,
                            appContext.getString(R.string.notification_primary_button), translatePendingIntent)

        } else if (myNotification.translateType == TranslateType.SECONDARY) {
            Log.d(TAG, "case for secondary language, translating to primary")

            App.state = TranslateType.SECONDARY

            builder.setContentTitle(myNotification.secondaryLangWord)
                    .setContentText(myNotification.secondaryLangDescription)
                    .addAction(R.drawable.ic_translate_black_24dp,
                            appContext.getString(R.string.notification_secondary_button), translatePendingIntent)
        }

        return builder

    }

    private fun getActivityStartIntent(appContext: Context, myNotification: MyNotification): PendingIntent {
        val intent = Intent(appContext, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.putExtra(NotificationConfig.NOTIFICATION_BUNDLE_TAG, myNotification)
        return PendingIntent.getActivity(appContext, 0, intent, 0)
    }

    private fun getTranslatePendingIntent(appContext: Context, myNotification: MyNotification): PendingIntent {

        val intent = Intent()
        intent.action = NotificationConfig.NOTIFICATION_INTENT_ACTION
        intent.putExtra(NotificationConfig.NOTIFICATION_BUNDLE_TAG, myNotification)
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
        Log.d(TAG, "showTheNotification() on UI")
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NotificationProvider().GLOBAL_NOTIFICATION_ID, notificationBuilder.build())
    }
}