package com.tandem.tandemtest.core.api

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Mahsa on 2022.02.26
 */
abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Result<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<Result<T>>) =
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {

                val result = when (val code = response.code()) {
                    in 200..299 -> {
                        val body = response.body()
                        Result.Success<T>(body)
                    }

                    401 -> Result.Error(
                        Exception("UnAuthorized")
                    )
                    in 400..499 -> Result.Error(
                        Exception("ClientError")
                    )
                    in 500..599 -> Result.Error(
                        Exception("ServerError")
                    )
                    else -> Result.Error(
                        Exception("UnExpectedError")
                    )
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val result = if (t is IOException) {
                    Result.Error(
                        Exception("NetworkError")
                    )
                } else {
                    Result.Error(
                        t as Exception
                    )
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }
        })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}

class ResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Result<Type>> =
        ResultCall(call)
}

class MyCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}