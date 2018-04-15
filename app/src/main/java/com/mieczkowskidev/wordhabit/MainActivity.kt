package com.mieczkowskidev.wordhabit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.utils.BuildTypeHelper
import com.mieczkowskidev.wordhabit.utils.visible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BuildTypeHelper.onlyDebug().let { developer_version_info.visible() }

        NotificationProvider().hideNotification(this)

        readFromBundle(intent.extras)
    }

    private fun readFromBundle(extras: Bundle?) {

        val myNotification = extras?.getParcelable(NotificationConfig.NOTIFICATION_BUNDLE_TAG) as MyNotification?

        if (myNotification != null) {
            setViews(myNotification)
        } else {
            empty_layout.visibility = View.VISIBLE
        }
    }

    private fun setViews(myNotification: MyNotification) {
        notification_layout.visibility = View.VISIBLE

        Glide.with(this).load(myNotification.image).into(image)

        title_language_text.text = myNotification.primaryLangWord
        desc_language_text.text = myNotification.primaryLangDescription

        change_lang_button.setOnTouchListener { view, motionEvent ->
            when {
                motionEvent.action == MotionEvent.ACTION_DOWN -> {
                    title_language_text.text = myNotification.secondaryLangWord
                    desc_language_text.text = myNotification.secondaryLangDescription
                    true
                }
                motionEvent.action == MotionEvent.ACTION_UP -> {
                    title_language_text.text = myNotification.primaryLangWord
                    desc_language_text.text = myNotification.primaryLangDescription
                    true
                }
                else -> false
            }
        }
    }
}
