package com.mieczkowskidev.wordhabit.app

import android.content.Context
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
}