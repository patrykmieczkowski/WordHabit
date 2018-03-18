package com.mieczkowskidev.wordhabit

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
        }

        if (p0.notification != null) {
            Log.d(TAG, "Notification body ${p0.notification.body} ")
        }
    }
}