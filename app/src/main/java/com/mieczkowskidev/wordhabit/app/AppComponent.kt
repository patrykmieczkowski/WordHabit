package com.mieczkowskidev.wordhabit.app

import android.content.BroadcastReceiver
import android.content.Context
import com.mieczkowskidev.wordhabit.model.LittleModel
import dagger.Component

/**
 * Created by Patryk Mieczkowski on 18.04.2018
 */
@AppScope
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun getAppContext(): Context

//    fun getBroadcastReceiver(): BroadcastReceiver

    fun getLittleModel(): LittleModel
}