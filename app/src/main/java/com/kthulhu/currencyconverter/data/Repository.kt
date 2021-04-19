package com.kthulhu.currencyconverter.data

import com.kthulhu.currencyconverter.data.db.Currency
import com.kthulhu.currencyconverter.data.db.ExchangeDao
import com.kthulhu.currencyconverter.data.networking.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(
        private val restApi: RestApi,
        private val exchangeDao: ExchangeDao
) {

    fun getRates(job: Job): Flow<Map<String, Double>> {
        loadRatesFromNet(job)
        return exchangeDao
                .retrieveRates()
                .flowOn(Dispatchers.Default)
                .map { it.toMap() }
                .flowOn(Dispatchers.IO)
    }

    private suspend fun cacheRates(response: ResponseDTO) {
        val ratesList: List<Currency> = response.rates.map { Currency(it.key, it.value) }
        exchangeDao.cacheRates(ratesList)
    }

    private fun loadRatesFromNet(job: Job) = GlobalScope.launch(Dispatchers.IO + job){
        when(val result = safeApiCall { restApi.getData() }) {
            is ResultWrapper.Success -> {
                cacheRates(result.value)
            }

            is ResultWrapper.Error -> {
                logException(result.throwable)
            }
        }
    }

    private fun List<Currency>.toMap(): Map<String, Double> {
        val map = mutableMapOf<String, Double>()
        this.forEach {
            map[it.name] = it.rate
        }
        return map
    }
}