package com.kakapo.model

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import com.kakapo.model.Currency as CommonCurrency

actual fun Double.toFormatCurrency(currency: CommonCurrency): String {
    val locale = Locale(currency.languageCode, currency.countryCode)
    val formatter = NumberFormat.getCurrencyInstance(locale)
    formatter.maximumFractionDigits = 0
    formatter.currency = Currency.getInstance(currency.name)
    return formatter.format(this)
}

actual fun Double.toFormatNumber(currency: CommonCurrency): String {
    val symbols = DecimalFormatSymbols(Locale(currency.languageCode, currency.symbol)).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val pattern = if (currency.baseMultiplier == 1) "#,###.##" else "#,###"
    val formatter = DecimalFormat(pattern, symbols)
    return formatter.format(this)
}