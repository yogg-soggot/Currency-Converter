package com.kthulhu.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.kthulhu.currencyconverter.R
import com.kthulhu.currencyconverter.di.AppComponent
import com.kthulhu.currencyconverter.di.AppModule
import com.kthulhu.currencyconverter.di.DaggerAppComponent
import com.kthulhu.currencyconverter.di.ViewModelFactory
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

        viewModel.getCurrencyNames()
        viewModel.cacheRates()

        addTextListeners()
        observeCurrency()
        observeCurrencyNames()
        addCurrencyChangeListeners()
    }

    private fun addTextListeners(){
        et_currency1.addTextChangedListener {
            if(isEditing||it.isNullOrEmpty()) return@addTextChangedListener
            viewModel.convertCurrency1(it.toString().toDouble())
        }

        et_currency2.addTextChangedListener {
            if (isEditing||it.isNullOrEmpty()) return@addTextChangedListener
            viewModel.convertCurrency2(it.toString().toDouble())
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
        viewModel.currency1ValueLiveData.observe(this, {
            setTextWithoutTriggeringTextChange {
                et_currency1.setText(it.toString())
            }
        })

        viewModel.currency2ValueLiveData.observe(this, {
            setTextWithoutTriggeringTextChange {
                et_currency2.setText(it.toString())
            }
        })
    }

    private fun setTextWithoutTriggeringTextChange(setText: () -> Unit){
        isEditing = true
        setText()
        isEditing = false
    }

    private fun observeCurrencyNames(){
        viewModel.currency1NameLiveData.observe(this, {
            currencyName.text = it
        })
        viewModel.currency2NameLiveData.observe(this, {
            currencyName2.text = it
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