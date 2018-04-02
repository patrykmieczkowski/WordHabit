package com.mieczkowskidev.wordhabit

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Patryk Mieczkowski on 18.03.2018
 */
class MyFirebaseMessageHandler : FirebaseMessagingService() {

    companion object {
        private val TAG = MyFirebaseMessageHandler::class.java.simpleName
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d(TAG, "Message from ${p0?.from}")

        if (p0?.data?.isNotEmpty()!!) {
            Log.d(TAG, "Message data paylod ${p0.data}")
            handleNotification(applicationContext, p0.data)
        }

        Log.d(TAG, "Notification body ${p0.notification?.body} ")

    }

    private fun handleNotification(appContext: Context, payload: Map<String, String>) =
            NotificationProvider().createNotification(appContext,
                    MyNotification(payload["title"], payload["desc_eng"], payload["desc_pl"]))
}
