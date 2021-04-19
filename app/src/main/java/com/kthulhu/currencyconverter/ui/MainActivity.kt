package com.kthulhu.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.kthulhu.currencyconverter.R
import com.kthulhu.currencyconverter.di.AppComponent
import com.kthulhu.currencyconverter.di.AppModule
import com.kthulhu.currencyconverter.di.DaggerAppComponent
import com.kthulhu.currencyconverter.di.ViewModelFactory
import com.kthulhu.currencyconverter.domain.toMoney
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appComponent: AppComponent

    private var isEditing = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()

        setInputFilters()
        addTextListeners()
        observeCurrency()
        observeCurrencyNames()
        addCurrencyChangeListeners()
    }

    private fun setInputFilters(){
        et_currency1.filters = arrayOf(DecimalDigitsInputFilter(4))
        et_currency2.filters = arrayOf(DecimalDigitsInputFilter(4))
    }

    private fun addTextListeners(){
        et_currency1.addTextChangedListener {
            if(isEditing) {
                return@addTextChangedListener
            }
            if(it.isNullOrEmpty()){
                et_currency2.setTextWithoutTriggeringTextChange("")
                return@addTextChangedListener
            }
            viewModel.convertCurrency1(it.toMoney())
        }

        et_currency2.addTextChangedListener {
            if (isEditing) {
                return@addTextChangedListener
            }
            if(it.isNullOrEmpty()){
                et_currency1.setTextWithoutTriggeringTextChange("")
                return@addTextChangedListener
            }
            viewModel.convertCurrency2(it.toMoney())
        }
    }

    private fun addCurrencyChangeListeners(){
        currencyName.setOnClickListener {
            viewModel.editedFieldNumber = 1
            viewModel.previousCurrencyName = (it as TextView).text.toString()
            showChooseCurrencyDialog()
        }
        currencyName2.setOnClickListener {
            viewModel.editedFieldNumber = 2
            viewModel.previousCurrencyName = (it as TextView).text.toString()
            showChooseCurrencyDialog()
        }
    }

    private fun showChooseCurrencyDialog(){
        ChooseCurrencyDialogFragment().show(supportFragmentManager, ChooseCurrencyDialogFragment.TAG)
    }

    private fun observeCurrency(){
        viewModel.currency1AmountLiveData.observe(this, {
            et_currency1.setTextWithoutTriggeringTextChange(it.toString())

        })

        viewModel.currency2AmountLiveData.observe(this, {
            et_currency2.setTextWithoutTriggeringTextChange(it.toString())
        })
    }

    private fun EditText.setTextWithoutTriggeringTextChange(text: String){
        isEditing = true
        this.setText(text)
        isEditing = false
    }

    private fun observeCurrencyNames(){
        viewModel.currency1NameLiveData.observe(this, {
            currencyName.text = it
            val currencyValue = et_currency1.text
            if(currencyValue.isNullOrEmpty()) return@observe
            viewModel.convertCurrency1(currencyValue.toMoney())
        })
        viewModel.currency2NameLiveData.observe(this, {
            currencyName2.text = it
            val currencyValue = et_currency2.text
            if(currencyValue.isNullOrEmpty()) return@observe
            viewModel.convertCurrency2(currencyValue.toMoney())
        })
    }

    private fun injectDependencies(){
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(applicationContext))
            .build()

        appComponent.inject(this)
    }
}