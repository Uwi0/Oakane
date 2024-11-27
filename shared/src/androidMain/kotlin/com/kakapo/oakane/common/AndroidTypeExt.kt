package com.kakapo.oakane.common

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

actual fun Double.toFormatIDRCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.maximumFractionDigits = 0
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(this)
}