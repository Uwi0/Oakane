package com.kakapo.oakane.common

fun String.asTextEllipsis(maxLine: Int): String {
    return if (this.length > maxLine) this.take(maxLine) + "..."
    else this
}

fun String.toColorInt(): Int {
    return if (this.startsWith("0x")) {
        this.removePrefix("0x").toLong(16).toInt()
    } else {
        this.toLong(16).toInt()
    }
}