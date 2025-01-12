package com.kakapo.model

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