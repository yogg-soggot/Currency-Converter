package com.kthulhu.currencyconverter.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun setRatesLoaded()
    fun areRatesLoaded(): LiveData<Boolean>
    fun getRates(job: Job): Flow<Map<String, Double>>
    fun loadRatesFromNet(): Job
}