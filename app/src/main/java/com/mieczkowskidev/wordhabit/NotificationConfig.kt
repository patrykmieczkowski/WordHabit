package com.mieczkowskidev.wordhabit

import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.model.TranslateType
import com.mieczkowskidev.wordhabit.utils.getRandom

/**
 * Created by Patryk Mieczkowski on 08.04.2018
 */
class NotificationConfig {

    companion object {
        const val NOTIFICATION_BUNDLE_TAG = "myNotification"
        const val NOTIFICATION_INTENT_ACTION = BuildConfig.APPLICATION_ID + ".MY_DATA"
        const val DEFAULT_TOPIC = "english"
        const val QA_TOPIC = "english_qa"
    }

    val mock1 = MyNotification("chair", "A thing on which you seat on",
            "krzeslo", "Rzecz na ktorej siadasz", null, TranslateType.PRIMARY)

    val mock2 = MyNotification("house", "You live in it",
            "dom", "Mieszkasz w nim", null, TranslateType.PRIMARY)

    val mock3 = MyNotification("chewing gum", "You are putting it into your mouth to get fresh breath",
            "guma do rzucia", "Rzujesz sobie to", null, TranslateType.PRIMARY)

    fun getMockNotification(): MyNotification {
        val dataSet = listOf(mock1, mock2, mock3)
        return dataSet.getRandom()
    }
}