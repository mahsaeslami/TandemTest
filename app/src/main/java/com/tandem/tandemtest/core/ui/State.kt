package com.tandem.tandemtest.core.ui

/**
 * Created by Mahsa on 2022.02.26
 *
 * This class is built for UI State return success, error or loading
 */
sealed class State<out T> {
    data class Success<T>(val data: T?) : State<T>()
    data class Error(val error: String) : State<Nothing>()
    object Loading : State<Nothing>()
}