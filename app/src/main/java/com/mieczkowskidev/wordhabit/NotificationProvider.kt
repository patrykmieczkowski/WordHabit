package com.mieczkowskidev.wordhabit

import android.app.PendingIntent
import android.content.Context
import android.support.v4.app.NotificationCompat

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
}