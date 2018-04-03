package com.mieczkowskidev.wordhabit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic("english")

        readFromBundle(intent.extras)
//        NotificationProvider().createNotification(this.applicationContext,
//                MyNotification("House", "A place where you live",
//                        "Miejsce gdzie sobie mieszkasz", "a", "b"))

    }

    private fun readFromBundle(extras: Bundle?) {
        Log.d(TAG, "reading from bundle")

        val noti = extras?.getSerializable("myNotification") as MyNotification?

        Log.d(TAG, "noti body: $noti")

        hello_text.text = noti?.secondaryLangDescription
    }
}
