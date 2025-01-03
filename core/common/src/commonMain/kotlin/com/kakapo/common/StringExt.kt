package com.kakapo.common

import co.touchlab.kermit.Logger

fun String.asTextEllipsis(maxLine: Int): String {
    return if (this.length > maxLine) this.take(maxLine) + "..."
    else this
}

fun String.toColorInt(): Int {
    val normalized = this.uppercase().replace("0X", "")
    return normalized.toLong(16).toInt()
}

fun String.asDouble(): Double {
    return try {
        if (this.isEmpty()) 0.0 else this.toDouble()
    }catch (e: Exception) {
        Logger.e(throwable = e, messageString = "error convert $this to double")
        0.0
    }
}

fun String.asInt(): Int {
    return try {
        if (this.isEmpty()) 0 else this.toInt()
    } catch (e: Exception) {
        Logger.e(throwable = e, messageString = "error convert $this to int")
        0
    }
}