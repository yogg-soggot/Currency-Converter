package com.kthulhu.currencyconverter.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey
    val name: String,
    val rate: Double
)