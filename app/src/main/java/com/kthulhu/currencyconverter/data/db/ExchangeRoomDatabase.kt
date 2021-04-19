package com.kthulhu.currencyconverter.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Currency::class, NextUpdate::class], version = 1)
abstract class ExchangeRoomDatabase : RoomDatabase(){
    abstract fun exchangeDao(): ExchangeDao

    companion object {
        @Volatile
        private var INSTANCE: ExchangeRoomDatabase? = null

        fun getDatabase(context: Context): ExchangeRoomDatabase {
            INSTANCE?.let { return it }

            synchronized(this) {
                return Room.databaseBuilder(
                    context,
                    ExchangeRoomDatabase::class.java,
                    "exchange_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}