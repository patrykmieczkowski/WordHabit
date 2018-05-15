package com.mieczkowskidev.wordhabit.app

import android.app.Application
import android.content.IntentFilter
import android.util.Log
import com.mieczkowskidev.wordhabit.MyBroadcastReceiver
import com.mieczkowskidev.wordhabit.NotificationConfig
import com.mieczkowskidev.wordhabit.NotificationProvider
import com.mieczkowskidev.wordhabit.model.TranslateType
import com.mieczkowskidev.wordhabit.utils.SharedPrefsManager

/**
 * Created by Patryk Mieczkowski on 05.04.2018
 */
val prefs: SharedPrefsManager by lazy {
    App.prefs!!
}

class App : Application() {

//    @Inject
//    lateinit var receiver: BroadcastReceiver

    lateinit var appComponent: AppComponent

    var notificationProvider: NotificationProvider? = null

    var receiver: MyBroadcastReceiver = MyBroadcastReceiver()


    companion object {
        private val TAG = App::class.java.simpleName
        var state: TranslateType? = null
        var prefs: SharedPrefsManager? = null
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
        initBroadcastReceiver()
        prefs = SharedPrefsManager(this)
        createNotificationProvider()

        Log.d(TAG, "Creating app")
    }

    fun createNotificationProvider() {
        notificationProvider = NotificationProvider(this, receiver)
    }

    private fun initBroadcastReceiver() {
        Log.d(NotificationProvider.TAG, "registerBroadcastReceiver() for ${NotificationConfig.NOTIFICATION_INTENT_ACTION}")
        val filter = IntentFilter()
        filter.addAction(NotificationConfig.NOTIFICATION_INTENT_ACTION)
        registerReceiver(receiver, filter)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}