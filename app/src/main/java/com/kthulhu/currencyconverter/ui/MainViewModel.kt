package com.kthulhu.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kthulhu.currencyconverter.domain.IConvertInteractor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val convertInteractor: IConvertInteractor
) : ViewModel() {

    lateinit var currencyNames: List<String>

    var editedFieldNumber = 1
    var previousCurrencyName = DEFAULT_CURRENCY

    private val _currency1NameLiveData = MutableLiveData(DEFAULT_CURRENCY)
    val currency1NameLiveData: LiveData<String> = _currency1NameLiveData

    private val _currency1ValueLiveData = MutableLiveData<Double>()
    val currency1ValueLiveData: LiveData<Double> = _currency1ValueLiveData

    private val _currency2NameLiveData = MutableLiveData(DEFAULT_CURRENCY)
    val currency2NameLiveData: LiveData<String> = _currency2NameLiveData

    private val _currency2ValueLiveData = MutableLiveData<Double>()
    val currency2ValueLiveData: LiveData<Double> = _currency2ValueLiveData

    fun getCurrencyNames() {
        currencyNames = convertInteractor.getCurrencyNames()
    }

    fun cacheRates(){
        convertInteractor.cacheRates()
    }

    fun changeName(name: String = previousCurrencyName) {
        when(editedFieldNumber){
            1 -> _currency1NameLiveData.postValue(name)
            2 -> _currency2NameLiveData.postValue(name)
            else -> throw IllegalArgumentException()
        }
    }

    fun convertCurrency1(value: Double) {
        val name1 = _currency1NameLiveData.value!!
        val name2 = _currency2NameLiveData.value!!
        val newValue = convertInteractor.convertCurrency(value, name1, name2)
        _currency2ValueLiveData.postValue(newValue)
    }

    fun convertCurrency2(value: Double) {
        val name1 = _currency1NameLiveData.value!!
        val name2 = _currency2NameLiveData.value!!
        val newValue = convertInteractor.convertCurrency(value, name2, name1)
        _currency1ValueLiveData.postValue(newValue)
    }

    companion object {
        const val DEFAULT_CURRENCY = "USD"
    }
}