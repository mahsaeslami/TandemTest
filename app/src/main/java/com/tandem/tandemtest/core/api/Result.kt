package com.tandem.tandemtest.core.api

/**
 * Created by Mahsa on 2022.02.26
 *
 * This class return success or error in Api response
 */
sealed class Result<out T> {
    data class Success<T>(val data: T?) : Result<T>()
    data class Error(val e: Exception) : Result<Nothing>()
}

inline fun <T, R> Result<T>.map(map: (T?) -> R): Result<R> =
    when (this) {
        is Result.Success -> Result.Success<R>(
            map(this.data)
        )
        is Result.Error -> this
    }

inline fun <T> Result<T>.subscribeBy(
    onSuccess: (value: T?) -> Unit,
    onError: (error: Exception) -> Unit
): Result<T> =
    when (this) {
        is Result.Success -> {
            onSuccess(data)
            this
        }
        is Result.Error -> {
            onError(e)
            this
        }
    }

inline fun <T> Result<T>.doOnNext(
    onSuccess: (value: T?) -> Unit
): Result<T> =
    when (this) {
        is Result.Success -> {
            onSuccess(data)
            this
        }
        is Result.Error -> {
            this
        }
    }