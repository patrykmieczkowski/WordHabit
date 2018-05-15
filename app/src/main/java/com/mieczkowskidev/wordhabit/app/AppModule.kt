package com.mieczkowskidev.wordhabit.app

import android.content.BroadcastReceiver
import android.content.Context
import com.mieczkowskidev.wordhabit.MyBroadcastReceiver
import com.mieczkowskidev.wordhabit.model.LittleModel
import dagger.Module
import dagger.Provides

/**
 * Created by Patryk Mieczkowski on 18.04.2018
 */
@Module
class AppModule(private val app: App) {

    @Provides
    @AppScope
    fun provideAppContext(): Context = app.applicationContext

//    @Provides
//    @AppScope
//    fun provideBroadcastReceiver(): BroadcastReceiver = MyBroadcastReceiver()

    @Provides
    @AppScope
    fun provideLittleModel(): LittleModel = LittleModel("tramwaj")
}