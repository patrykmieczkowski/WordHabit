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
        var state: TranslateType? = null
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "Creating app")
    }

}