package com.kthulhu.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kthulhu.currencyconverter.domain.IConvertInteractor
import com.kthulhu.currencyconverter.domain.Money
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val convertInteractor: IConvertInteractor
) : ViewModel() {

    var editedFieldNumber = 1
    var previousCurrencyName = DEFAULT_CURRENCY

    private val _currency1NameLiveData = MutableLiveData(DEFAULT_CURRENCY)
    val currency1NameLiveData: LiveData<String> = _currency1NameLiveData

    private val _currency1AmountLiveData = MutableLiveData<Money>()
    val currency1AmountLiveData: LiveData<Money> = _currency1AmountLiveData

    private val _currency2NameLiveData = MutableLiveData(DEFAULT_CURRENCY)
    val currency2NameLiveData: LiveData<String> = _currency2NameLiveData

    private val _currency2AmountLiveData = MutableLiveData<Money>()
    val currency2AmountLiveData: LiveData<Money> = _currency2AmountLiveData

    fun areRatesLoaded(): LiveData<Boolean> = convertInteractor.areRatesLoaded()

    fun forceLoadRates() = convertInteractor.forceLoadRates()

    fun getCurrencyNames() : List<String> {
        return convertInteractor.getCurrencyNames()
    }

    fun changeName(name: String = previousCurrencyName) {
        when(editedFieldNumber){
            1 -> _currency1NameLiveData.postValue(name)
            2 -> _currency2NameLiveData.postValue(name)
            else -> throw IllegalArgumentException()
        }
    }

    fun convertCurrency1(amount: Money) {
        val name1 = _currency1NameLiveData.value!!
        val name2 = _currency2NameLiveData.value!!
        val newValue = convertInteractor.convertCurrency(amount, name1, name2)
        _currency2AmountLiveData.postValue(newValue)
    }

    fun convertCurrency2(amount: Money) {
        val name1 = _currency1NameLiveData.value!!
        val name2 = _currency2NameLiveData.value!!
        val newValue = convertInteractor.convertCurrency(amount, name2, name1)
        _currency1AmountLiveData.postValue(newValue)
    }

    override fun onCleared() {
        convertInteractor.stopAsyncOperations()
    }

    companion object {
        const val DEFAULT_CURRENCY = "USD"
    }
}