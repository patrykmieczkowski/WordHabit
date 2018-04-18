package com.mieczkowskidev.wordhabit.main.view

import android.content.BroadcastReceiver
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.mieczkowskidev.wordhabit.NotificationConfig
import com.mieczkowskidev.wordhabit.R
import com.mieczkowskidev.wordhabit.app.App
import com.mieczkowskidev.wordhabit.main.contract.MainContract
import com.mieczkowskidev.wordhabit.main.di.DaggerMainComponent
import com.mieczkowskidev.wordhabit.main.presenter.MainPresenter
import com.mieczkowskidev.wordhabit.model.MyNotification
import com.mieczkowskidev.wordhabit.utils.BuildTypeHelper
import com.mieczkowskidev.wordhabit.utils.consume
import com.mieczkowskidev.wordhabit.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter: MainContract.Presenter = MainPresenter()

//    @Inject
//    lateinit var receiver: BroadcastReceiver

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInjection()

        presenter.attach(this)
//        NotificationProvider().hideNotification(this)
        prepareListeners()

        readFromBundle(intent.extras)

//        Log.d(TAG, "receiver hash ${receiver.hashCode()}")
    }

    private fun initInjection() {

        DaggerMainComponent.builder()
                .appComponent((application as App).appComponent)
                .build()
                .injectMainActivity(this)
    }

    private fun prepareListeners() {
        BuildTypeHelper.onlyDebug().let {
            developer_version_button.apply {
                visible()
                setOnClickListener { presenter.switchTopicClicked() }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (BuildTypeHelper.onlyDebug()) {
            val inflater = menuInflater
            inflater.inflate(R.menu.main_menu, menu)
            true
        } else {
            super.onCreateOptionsMenu(menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_generate -> consume { presenter.generateMockNotification(this) }
        else -> super.onOptionsItemSelected(item)
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
