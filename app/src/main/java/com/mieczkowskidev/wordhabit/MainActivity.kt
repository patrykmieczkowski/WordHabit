package com.mieczkowskidev.wordhabit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic("english")

//        NotificationProvider().createNotification(this.applicationContext,
//                MyNotification("House", "A place where you live",
//                        "Miejsce gdzie sobie mieszkasz", "a", "b"))

    }
}
