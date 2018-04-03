package com.mieczkowskidev.wordhabit

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Single

/**
 * Created by Patryk Mieczkowski on 03.04.2018
 */
class MyImageManager {

    fun getBitmapSingle(appContext: Context, imageUrl: String): Single<Bitmap> = Single.create {
        Glide.with(appContext)
                .asBitmap()
                .load(imageUrl)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        it.onError(Exception("bitmap load fail - ${e.toString()}"))
                        return true
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any?, target: Target<Bitmap>, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        it.onSuccess(resource)
                        return true
                    }
                })

    }

}