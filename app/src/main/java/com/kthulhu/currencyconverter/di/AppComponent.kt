package com.kthulhu.currencyconverter.di

import com.kthulhu.currencyconverter.ui.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}