package com.kthulhu.currencyconverter.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job

interface IConvertInteractor {
    fun convertCurrency(
        amount: Money,
        currencyName1: String,
        currencyName2: String
    ): Money

    fun getCurrencyNames(): List<String>

    fun areRatesLoaded(): LiveData<Boolean>

    fun forceLoadRates(): Job

    fun stopAsyncOperations()
}