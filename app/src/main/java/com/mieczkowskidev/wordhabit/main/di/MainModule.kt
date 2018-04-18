package com.mieczkowskidev.wordhabit.main.di

import com.mieczkowskidev.wordhabit.main.view.MainActivity
import dagger.Module
import dagger.Provides

/**
 * Created by Patryk Mieczkowski on 18.04.2018
 */
@Module
class MainModule(private val mainActivity: MainActivity) {

    @Provides
    @MainScope
    fun provideMainActivity(): MainActivity = mainActivity
}