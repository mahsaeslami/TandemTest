package com.tandem.tandemtest.core.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * Created by Mahsa on 2022.02.26
 */
interface IImageLoader {
    fun load(
        context: Context,
        url: String?,
        imageView: ImageView
    )
}
