package com.kthulhu.currencyconverter.domain

interface IConvertInteractor {
    fun convertCurrency(
        value: Double,
        currencyName1: String,
        currencyName2: String
    ): Double

    fun getCurrencyNames(): List<String>

    fun cacheRates()
}