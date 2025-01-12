package com.kakapo.common

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale



actual fun Double.toFormatIDR(): String {
    val formatter = NumberFormat.getInstance()
    formatter.maximumFractionDigits = 0
    return formatter.format(this).replace(",", ".")
}