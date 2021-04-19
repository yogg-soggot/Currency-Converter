package com.kthulhu.currencyconverter.data.networking

import retrofit2.http.GET

interface RestApi {
    @GET("latest/")
    suspend fun getData(): ResponseDTO
}