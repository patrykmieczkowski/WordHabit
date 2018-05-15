package com.mieczkowskidev.wordhabit

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mieczkowskidev.wordhabit.app.App
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.model.TranslateType

/**
 * Created by Patryk Mieczkowski on 18.03.2018
 */
class MyFirebaseMessageHandler : FirebaseMessagingService() {

    companion object {
        private val TAG = MyFirebaseMessageHandler::class.java.simpleName
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived() - Message from topic ${p0?.from}")

        if (p0?.data?.isNotEmpty()!!) {
            Log.d(TAG, "onMessageReceived() message data payload ${p0.data}")
//            (applicationContext as App).createNotificationProvider()
            handleNotification(applicationContext, p0.data)
        }

    }

    private fun handleNotification(appContext: Context, payload: Map<String, String>) =
            (appContext as App).notificationProvider?.createNotificationAndReceiver(appContext,
                    MyNotification(payload["primaryLangWord"], payload["primaryLangDescription"],
                            payload["secondaryLangWord"], payload["secondaryLangDescription"],
                            payload["image"], TranslateType.PRIMARY))
}
