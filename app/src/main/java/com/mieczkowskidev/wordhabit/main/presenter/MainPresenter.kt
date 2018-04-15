package com.mieczkowskidev.wordhabit.main.presenter

import com.google.firebase.messaging.FirebaseMessaging
import com.mieczkowskidev.wordhabit.main.contract.MainContract
import com.mieczkowskidev.wordhabit.prefs

/**
 * Created by Patryk Mieczkowski on 15.04.2018
 */
class MainPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun attach(view: MainContract.View) {
        this.view = view

        registerToTopic()
    }

    private fun registerToTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic(prefs.topic)
    }

    override fun detach() {
        this.view = null
    }
}