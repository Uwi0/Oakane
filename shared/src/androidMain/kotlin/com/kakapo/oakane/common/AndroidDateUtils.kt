package com.kakapo.oakane.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun LocalDateTime.toFormatedString(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val date = Date(this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())
    return formatter.format(date)
}