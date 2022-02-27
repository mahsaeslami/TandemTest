package com.tandem.tandemtest.core.extension

import android.view.View

/**
 * Created by Mahsa on 2022.02.27
 */
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}