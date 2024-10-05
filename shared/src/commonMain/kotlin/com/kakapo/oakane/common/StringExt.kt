package com.kakapo.oakane.common

fun String.asTextEllipsis(maxLine: Int): String {
    return if (this.length > maxLine) this.take(maxLine) + "..."
    else this
}