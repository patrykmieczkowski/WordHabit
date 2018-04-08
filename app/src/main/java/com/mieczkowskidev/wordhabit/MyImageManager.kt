package com.mieczkowskidev.wordhabit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Single

/**
 * Created by Patryk Mieczkowski on 03.04.2018
 */
class MyImageManager {

    fun getBitmapSingle(appContext: Context, imageUrl: String): Single<Bitmap> = Single.create {
        Glide.with(appContext)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        it.onError(Exception("bitmap load fail"))
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Log.d(NotificationProvider.TAG, "bitmap download success - refreshing notification soon")
                        it.onSuccess(resource)
                    }
                })

    }

}