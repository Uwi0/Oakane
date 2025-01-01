package com.kakapo.common

fun Boolean.asLong(): Long {
    return if (this) 1 else 0
}

expect fun Double.toFormatIDRWithCurrency(): String
expect fun Double.toFormatIDR(): String