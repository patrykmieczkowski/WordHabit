package com.mieczkowskidev.wordhabit.utils

import android.content.Context
import com.mieczkowskidev.wordhabit.BuildConfig
import com.mieczkowskidev.wordhabit.NotificationConfig

/**
 * Created by Patryk Mieczkowski on 15.04.2018
 */
class SharedPrefsManager(appContext: Context) {

    private val topicName = "topic"
    private val prefsName = BuildConfig.APPLICATION_ID + ".prefs"
    private val sharedPrefs = appContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    var topic: String
        get() = sharedPrefs.getString(topicName, NotificationConfig.DEFAULT_TOPIC)
        set(value) = sharedPrefs.edit().putString(topicName, value).apply()
}