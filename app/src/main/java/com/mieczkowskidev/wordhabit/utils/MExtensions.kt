package com.mieczkowskidev.wordhabit.utils

import android.app.Activity
import android.util.Log
import android.view.View
import java.util.*

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

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun <E> List<E>.getRandom() = this[Random().nextInt(this.size)]