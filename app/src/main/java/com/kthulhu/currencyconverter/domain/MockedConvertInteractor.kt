package com.kthulhu.currencyconverter.domain

class MockedConvertInteractor: IConvertInteractor {

    override fun convertCurrency(
        amount: Money,
        currencyName1: String,
        currencyName2: String
    ): Money {
        return amount / rates[currencyName1]!! * rates[currencyName2]!!
    }

    override fun getCurrencyNames(): List<String> {
        return names
    }

    override fun cacheRates() = Unit

    companion object {
        val names = listOf("USD", "RUB", "EUR", "CAD", "GEL", "INR", "JOJ", "OGO", "LDE", "NWI", "NDE")
        val rates = hashMapOf("USD" to 1.0, "RUB" to 1.21, "EUR" to 1.31, "CAD" to 1.41, "GEL" to 1.51, "INR" to 1.61, "JOJ" to 1.71, "OGO" to 1.81, "LDE" to 1.91, "NWI" to 2.21, "NDE" to 2.31)
    }
}