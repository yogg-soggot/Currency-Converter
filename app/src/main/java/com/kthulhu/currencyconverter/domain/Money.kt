package com.kthulhu.currencyconverter.domain

import android.text.Editable
import java.math.BigDecimal
import java.math.RoundingMode

class Money(private val amount: BigDecimal) {

    override fun toString(): String {
        return amount.setScale(2, RoundingMode.DOWN).stripTrailingZeros().toString()
    }

    operator fun times(number: Double): Money {
        return Money(amount * number.toBigDecimal())
    }
    operator fun div(number: Double): Money {

        return amount.divide(number.toBigDecimal(), 4, RoundingMode.HALF_UP).toMoney()
    }
}

fun BigDecimal.toMoney(): Money {
    return Money(this)
}

fun String.toMoney(): Money {
    return this.toBigDecimal().toMoney()
}

fun Editable.toMoney(): Money{
    return this.toString().toMoney()
}