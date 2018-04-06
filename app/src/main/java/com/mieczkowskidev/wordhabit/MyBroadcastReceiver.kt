package com.mieczkowskidev.wordhabit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            val notificationData = bundle.getSerializable("myNotification") as MyNotification?

            Log.d(TAG, "MyBroadcastReceiver ${notificationData?.translateType}")

            if (notificationData != null) {
//                if (notificationData.translateType == "primary") {
//                    notificationData.translateType = "secondary"
//                } else if (notificationData.translateType == "secondary") {
//                    notificationData.translateType = "primary"
//                }

                NotificationProvider().createNotification(appContext, notificationData)
            }
        }
    }

}