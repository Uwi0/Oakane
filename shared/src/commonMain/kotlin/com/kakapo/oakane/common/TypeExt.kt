package com.kakapo.oakane.common

fun Boolean.asLong(): Long {
    return if (this) 1 else 0
}