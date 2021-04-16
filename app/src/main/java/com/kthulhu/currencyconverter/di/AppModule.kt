package com.kthulhu.currencyconverter.di

import android.content.Context
import com.kthulhu.currencyconverter.domain.IConvertInteractor
import com.kthulhu.currencyconverter.domain.MockedConvertInteractor
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideConvertInteractor(): IConvertInteractor {
        return MockedConvertInteractor()
    }

}