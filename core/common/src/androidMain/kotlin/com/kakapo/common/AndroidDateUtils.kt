package com.kakapo.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

actual fun LocalDateTime.toFormatedString(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
    return formatter.format(date)
}

actual fun Long.formatDateWith(pattern: String): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}

actual fun Long.daysBetween(otherDate: Long): Long {
    val differenceInTImeMillis = this - otherDate
    return TimeUnit.MILLISECONDS.toDays(differenceInTImeMillis)
}