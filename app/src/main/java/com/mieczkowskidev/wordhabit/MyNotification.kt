package com.mieczkowskidev.wordhabit

import java.io.Serializable

/**
 * Created by Patryk Mieczkowski on 02.04.2018
 */
data class MyNotification(var primaryLangWord: String?, var primaryLangDescription: String?,
                          var secondaryLangWord: String?, var secondaryLangDescription: String?,
                          var image: String?) : Serializable