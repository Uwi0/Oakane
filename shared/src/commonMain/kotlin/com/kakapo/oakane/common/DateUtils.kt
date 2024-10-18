package com.kakapo.oakane.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toDateWith(format: String): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.toFormatedString(format)
}

expect fun Long.formatDateWith(pattern: String): String

expect fun LocalDateTime.toFormatedString(pattern: String): String