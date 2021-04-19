package com.kthulhu.currencyconverter.data.networking

import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    val result: String,

    @SerializedName("time_next_update_unix")
    val nextUpdate: Int,

    val rates: Map<String, Double>
)