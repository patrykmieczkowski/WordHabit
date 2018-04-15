package com.mieczkowskidev.wordhabit.utils

import android.app.Activity
import android.util.Log
import android.view.View

/**
 * Created by Patryk Mieczkowski on 15.04.2018
 */
fun Activity.logd(message: String) {
    if (BuildTypeHelper.onlyDebug()) Log.d(this::class.java.simpleName, message)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}