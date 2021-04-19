package com.kthulhu.currencyconverter.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.kthulhu.currencyconverter.data.Repository
import com.kthulhu.currencyconverter.data.db.ExchangeDao
import com.kthulhu.currencyconverter.data.db.ExchangeRoomDatabase
import com.kthulhu.currencyconverter.data.networking.RestApi
import com.kthulhu.currencyconverter.domain.IConvertInteractor
import com.kthulhu.currencyconverter.domain.ConvertInteractor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideConvertInteractor(repository: Repository): IConvertInteractor {
        return ConvertInteractor(repository)
    }

    @Provides
    fun provideExchangeDao(): ExchangeDao{
        return ExchangeRoomDatabase.getDatabase(context).exchangeDao()
    }

    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://open.exchangerate-api.com/v6/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideRestApi(retrofit: Retrofit.Builder, client: OkHttpClient): RestApi {
        return retrofit
            .client(client)
            .build()
            .create(RestApi::class.java)
    }

}