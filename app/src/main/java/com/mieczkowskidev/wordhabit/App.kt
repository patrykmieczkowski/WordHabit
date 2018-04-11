package com.mieczkowskidev.wordhabit

import android.app.Application
import android.util.Log
import com.mieczkowskidev.wordhabit.model.TranslateType

/**
 * Created by Patryk Mieczkowski on 05.04.2018
 */
class App : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
//        var receiver = MyBroadcastReceiver()
//        var isRegistered = false
        var state: TranslateType? = null
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Creating app")
    }

    override fun onTerminate() {
        super.onTerminate()

//        if (!isRegistered) {
//            try {
//                if (App.receiver != null) {
//                    unregisterReceiver(App.receiver)
//                    isRegistered = false
//                }
//            } catch (e: IllegalArgumentException) {
//                Log.i(TAG, "my broadcast receiver is already unregistered")
//            }
//        }
//        Log.d(TAG, "Terminating an app")
    }
}