package com.kthulhu.currencyconverter.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kthulhu.currencyconverter.data.db.Currency
import com.kthulhu.currencyconverter.data.db.ExchangeDao
import com.kthulhu.currencyconverter.data.db.NextUpdate
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

    private val areRatesLoadedLiveData = MutableLiveData(false)
    fun setRatesLoaded() = areRatesLoadedLiveData.postValue(true)
    fun areRatesLoaded(): LiveData<Boolean> = areRatesLoadedLiveData

    fun getRates(job: Job): Flow<Map<String, Double>> {
        loadIfHasUpdate(job)
        log("Subscribed on rates in db")
        return exchangeDao
                .retrieveRates()
                .flowOn(Dispatchers.Default)
                .map { it.toMap() }
                .flowOn(Dispatchers.IO)
    }

    private suspend fun cacheRates(response: ResponseDTO) {
        log("Rates cached to db")
        val ratesList: List<Currency> = response.rates.map { Currency(it.key, it.value) }
        val updateTime = NextUpdate(response.nextUpdate)
        exchangeDao.cacheRates(ratesList)
        exchangeDao.saveUpdateTime(updateTime)
    }

    private fun loadIfHasUpdate(job: Job) = GlobalScope.launch(Dispatchers.IO + job) {
        val nextUpdate: Int = exchangeDao.getUpdateTime() ?: WAS_NEVER_UPDATED
        val currentTime = System.currentTimeMillis() / 1000
        if(currentTime > nextUpdate) {
            log("Data is not updated, loading from net...")
            loadRatesFromNet()
        } else {
            log("Data is up to date")
            setRatesLoaded()
        }
    }

    fun loadRatesFromNet() = GlobalScope.launch(Dispatchers.IO){
        when(val result = safeApiCall { restApi.getData() }) {
            is ResultWrapper.Success -> {
                log("Data is loaded from net, caching")
                cacheRates(result.value)
            }

            is ResultWrapper.Error -> {
                log("Error loading data")
                logException(result)
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

    private fun log(msg: String){
        Log.d("Data flow", msg)
    }

    companion object {
        const val WAS_NEVER_UPDATED = 0
    }
}