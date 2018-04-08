package com.mieczkowskidev.wordhabit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.model.TranslateType

/**
 * Created by Patryk Mieczkowski on 04.04.2018
 */
class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = MyBroadcastReceiver::class.java.simpleName
    }

    override fun onReceive(appContext: Context, intent: Intent) {

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            val notificationData = bundle.getParcelable(NotificationConfig.NOTIFICATION_BUNDLE_TAG) as MyNotification?

            if (notificationData != null) {

                if (App.state == TranslateType.PRIMARY) {
                    notificationData.translateType = TranslateType.SECONDARY
                } else {
                    notificationData.translateType = TranslateType.PRIMARY
                }

                Log.d(TAG, "MyBroadcastReceiver receive data, translating to ${notificationData.translateType}")

                NotificationProvider().createNotification(appContext, notificationData)
            }
        }
    }

}