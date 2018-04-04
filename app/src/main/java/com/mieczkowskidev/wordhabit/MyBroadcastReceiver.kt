package com.mieczkowskidev.wordhabit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by Patryk Mieczkowski on 04.04.2018
 */
class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = MyBroadcastReceiver::class.java.simpleName
    }

    override fun onReceive(appContext: Context, intent: Intent) {
        Log.d(TAG, "MyBroadcastReceiver on Receive data ()")

        if (intent.extras != null) {
            val notificationData = intent.extras.getSerializable("myNotification") as MyNotification?

            if (notificationData != null) {
                NotificationProvider().createNotification(appContext, notificationData, TranslateType.SECONDARY)
            }
        }
    }

}