package com.kthulhu.currencyconverter.domain

interface IConvertInteractor {
    fun convertCurrency(
        amount: Money,
        currencyName1: String,
        currencyName2: String
    ): Money

    fun getCurrencyNames(): List<String>

    fun cacheRates()
}