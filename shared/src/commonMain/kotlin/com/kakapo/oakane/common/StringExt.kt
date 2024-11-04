package com.kakapo.oakane.common

fun String.asTextEllipsis(maxLine: Int): String {
    return if (this.length > maxLine) this.take(maxLine) + "..."
    else this
}

fun String.toColorInt(): Int {
    val normalized = this.uppercase().replace("0X", "")
    return normalized.toLong(16).toInt()
}