package com.kthulhu.currencyconverter.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConvertInteractor @Inject constructor(
        private val repository: IRepository
): IConvertInteractor {

    private val job = Job()

    init {
        GlobalScope.launch(Dispatchers.IO + job) {
            repository.getRates(job).collect {
                rates = it
                names = it.keys.toList()
                if(it.isNotEmpty()) repository.setRatesLoaded()
            }
        }
    }

    override fun areRatesLoaded() = repository.areRatesLoaded()

    override fun forceLoadRates() = repository.loadRatesFromNet()

    override fun stopAsyncOperations() {
        job.cancel()
    }

    private var names = listOf<String>()
    private var rates = mapOf<String, Double>()

    override fun convertCurrency(
            amount: Money,
            currencyName1: String,
            currencyName2: String
    ): Money {
        return try {
            amount / rates[currencyName1]!! * rates[currencyName2]!!
        } catch (e: NullPointerException) {
            amount
        }
    } // На случай если нет интернета и данные ещё не кэшировались. В этом случае в обоих полях стоит USD

    override fun getCurrencyNames(): List<String> {
        return names
    }
}