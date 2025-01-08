package com.kakapo.common

fun Boolean.asLong(): Long {
    return if (this) 1 else 0
}

expect fun Double.toFormatCurrency(): String
expect fun Double.toFormatIDR(): String