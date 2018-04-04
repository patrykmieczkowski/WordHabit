package com.mieczkowskidev.wordhabit

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
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

//        registerRece()
//        sendData()

        readFromBundle(intent.extras)
//        NotificationProvider().createNotification(this.applicationContext,
//                MyNotification("House", "A place where you live",
//                        "Mieaajsce gdzie sobie mieszkasz", "a", "b"))

    }

    private fun sendData() {
        send_button.setOnClickListener { view ->
            val notiData = MyNotification("Bin", "A place where you put trash", "Kosz", "Miejsca na smieci", null)
            val intent = Intent()
            intent.action = "com.mieczkowskidev.wordhabit.MY_DATA"
            intent.putExtra("myNotification", notiData)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        }

    }

    private fun registerRece() {
        val recei = MyBroadcastReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        filter.addAction("com.mieczkowskidev.wordhabit.MY_DATA")
        LocalBroadcastManager.getInstance(this).registerReceiver(recei, filter)
    }

    private fun readFromBundle(extras: Bundle?) {
        Log.d(TAG, "reading from bundle")

        val noti = extras?.getSerializable("myNotification") as MyNotification?

        Log.d(TAG, "noti body: $noti")

        hello_text.text = noti?.secondaryLangDescription
    }
}
