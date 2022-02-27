package com.tandem.tandemtest.presentation.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.tandem.tandemtest.core.imageloader.IImageLoader

/**
 * Created by Mahsa on 2022.02.27
 */
class GlideImageLoader : IImageLoader {
    companion object {
        private const val CARD_CURVE_SIZE = 4
    }

    private fun getOptionCurve() =
        RequestOptions().transform(RoundedCorners(CARD_CURVE_SIZE))

    override fun load(
        context: Context,
        url: String?,
        imageView: ImageView
    ) {
        context.let {
            val glideReq = Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(getOptionCurve())

            glideReq.into(imageView)
        }
    }
}