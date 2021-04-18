package com.kthulhu.currencyconverter.ui

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class DecimalDigitsInputFilter(digitsAfterZero: Int) : InputFilter {

    private var pattern: Pattern = Pattern.compile("[0-9]+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?")

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        val matcher = pattern.matcher(dest.toString())
        if(!matcher.matches()) return ""
        return null
    }
}