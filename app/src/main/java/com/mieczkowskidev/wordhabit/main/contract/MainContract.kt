package com.mieczkowskidev.wordhabit.main.contract

import android.content.Context

/**
 * Created by Patryk Mieczkowski on 15.04.2018
 */
interface MainContract {

    interface View {

        fun showInfoHeader(topic: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun switchTopicClicked()
        fun generateMockNotification(context: Context)
    }
}