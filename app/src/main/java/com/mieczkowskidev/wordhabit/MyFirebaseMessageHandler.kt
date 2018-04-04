package com.mieczkowskidev.wordhabit

import android.content.Context
import android.content.IntentFilter
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
        Log.d(TAG, "w onMessageReceived - Message from ${p0?.from}")

        if (p0?.data?.isNotEmpty()!!) {
            Log.d(TAG, "Message data paylod ${p0.data}")
            registerBroadcastReceiver(applicationContext)
            handleNotification(applicationContext, p0.data)
        }

    }

    private fun registerBroadcastReceiver(appContext: Context) {
        Log.d(TAG, "registerBroadcastReceiver() for com.mieczkowskidev.wordhabit.MY_DATA")
        val filter = IntentFilter()
        filter.addAction("com.mieczkowskidev.wordhabit.MY_DATA")
        appContext.registerReceiver(MyBroadcastReceiver(), filter)
    }

    private fun handleNotification(appContext: Context, payload: Map<String, String>) =
            NotificationProvider().createNotification(appContext,
                    MyNotification(payload["primaryLangWord"], payload["primaryLangDescription"],
                            payload["secondaryLangWord"], payload["secondaryLangDescription"],
                            payload["image"]), TranslateType.PRIMARY)
}
