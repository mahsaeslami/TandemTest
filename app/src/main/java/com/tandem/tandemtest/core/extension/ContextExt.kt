package com.tandem.tandemtest.core.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by Mahsa on 2022.02.27
 */
fun Context.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message + "", duration).show()