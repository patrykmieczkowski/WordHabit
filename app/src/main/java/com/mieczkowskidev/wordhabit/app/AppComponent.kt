package com.mieczkowskidev.wordhabit.app

import android.content.Context
import dagger.Component

/**
 * Created by Patryk Mieczkowski on 18.04.2018
 */
@AppScope
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun getAppContext(): Context
}