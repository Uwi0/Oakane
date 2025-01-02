package com.kakapo.common

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

actual fun Double.toFormatIDRWithCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.maximumFractionDigits = 0
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(this)
}

actual fun Double.toFormatIDR(): String {
    val formatter = NumberFormat.getInstance()
    formatter.maximumFractionDigits = 0
    return formatter.format(this).replace(",", ".")
}