package com.kthulhu.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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




    }

    private fun addTextListeners(){
        et_currency1.addTextChangedListener {
            if(isEditing) return@addTextChangedListener
            viewModel.convertCurrency1(it.toString().toDouble())
        }

        et_currency2.addTextChangedListener {
            if (isEditing) return@addTextChangedListener
            viewModel.convertCurrency2(it.toString().toDouble())
        }
    }

    private fun observeCurrency(){
        viewModel.currency1ValueLiveData.observe(this, {
            isEditing = true
            et_currency1.setText(it.toString())
            isEditing = false
        })

        viewModel.currency2ValueLiveData.observe(this, {
            isEditing = true
            et_currency2.setText(it.toString())
            isEditing = false
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