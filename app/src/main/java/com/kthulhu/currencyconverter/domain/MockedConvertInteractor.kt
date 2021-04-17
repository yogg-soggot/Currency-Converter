package com.kthulhu.currencyconverter.domain

class MockedConvertInteractor: IConvertInteractor {

    override fun convertCurrency(
        value: Double,
        currencyName1: String,
        currencyName2: String
    ): Double {
        return value * 2
    }

    override fun getCurrencyNames(): List<String> {
        return names
    }

    override fun cacheRates() = Unit

    companion object {
        val names = listOf("USD", "RUB", "EUR", "CAD", "GEL", "INR", "JOJ", "OGO", "LDE", "NWI", "NDE")
    }
}