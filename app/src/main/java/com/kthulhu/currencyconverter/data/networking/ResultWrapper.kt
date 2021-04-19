package com.kthulhu.currencyconverter.data.networking

import android.util.Log

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val throwable: Throwable, val code: Int? = null, val error: String? = null): ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(apiCall())
    } catch (e: Throwable) {
        ResultWrapper.Error(e)
    }
}

fun logException(t: Throwable) {
    Log.e("Network Error", t.message.toString())
}