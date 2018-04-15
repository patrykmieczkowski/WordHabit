package com.mieczkowskidev.wordhabit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.mieczkowskidev.wordhabit.main.contract.MainContract
import com.mieczkowskidev.wordhabit.main.presenter.MainPresenter
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.utils.BuildTypeHelper
import com.mieczkowskidev.wordhabit.utils.visible
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter: MainContract.Presenter = MainPresenter()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attach(this)
//        NotificationProvider().hideNotification(this)
        prepareListeners()

        readFromBundle(intent.extras)
    }

    private fun prepareListeners() {
        BuildTypeHelper.onlyDebug().let {
            developer_version_button.apply {
                visible()
                setOnClickListener { presenter.switchTopicClicked() }
            }
        }
    }

    override fun showInfoHeader(topic: String) {
        val infoText = getString(R.string.developer_version_info) + " $topic"
        developer_version_info.text = infoText
        developer_version_info.visible()
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
