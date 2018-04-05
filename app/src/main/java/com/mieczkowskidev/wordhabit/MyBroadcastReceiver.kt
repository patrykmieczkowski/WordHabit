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
            val extrasTranslateType = bundle.getString("translate") as String?

            Log.d(TAG, "MyBroadcastReceiver $extrasTranslateType")

//            val translateType = if (extrasTranslateType == TranslateType.PRIMARY) {
//                TranslateType.SECONDARY
//            } else {
//                TranslateType.PRIMARY
//            }

            if (notificationData != null) {
                NotificationProvider().createNotification(appContext, notificationData, TranslateType.SECONDARY)
            }
        }
    }

}