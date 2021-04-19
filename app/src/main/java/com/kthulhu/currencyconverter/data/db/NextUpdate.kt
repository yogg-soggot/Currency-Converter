package com.kthulhu.currencyconverter.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "next_update")
data class NextUpdate(
    val timeUnix: Int,
    @PrimaryKey
    val id: Int = 1
)