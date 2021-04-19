package com.kthulhu.currencyconverter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheRates(rates: List<Currency>): Void

    @Query("SELECT * FROM currency_table")
    fun retrieveRates(): Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUpdateTime(update: NextUpdate): Void

    @Query("SELECT timeUnix FROM next_update")
    suspend fun getUpdateTime(): Int
}