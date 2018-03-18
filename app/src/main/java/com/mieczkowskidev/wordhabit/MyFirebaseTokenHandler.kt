package com.mieczkowskidev.wordhabit

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by Patryk Mieczkowski on 18.03.2018
 */
class MyFirebaseTokenHandler : FirebaseInstanceIdService() {

    companion object {
        private val TAG = MyFirebaseTokenHandler::class.java.simpleName
    }

    override fun onTokenRefresh() {

        val currentToken = FirebaseInstanceId.getInstance().token

        Log.d(TAG, "FCM Token: $currentToken")
    }
}