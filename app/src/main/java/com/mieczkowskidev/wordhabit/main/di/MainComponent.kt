package com.mieczkowskidev.wordhabit.main.di

import com.mieczkowskidev.wordhabit.app.AppComponent
import com.mieczkowskidev.wordhabit.main.view.MainActivity
import dagger.Component

/**
 * Created by Patryk Mieczkowski on 18.04.2018
 */
@MainScope
@Component(modules = [(MainModule::class)], dependencies = [(AppComponent::class)])
interface MainComponent {

    fun injectMainActivity(mainActivity: MainActivity)

}