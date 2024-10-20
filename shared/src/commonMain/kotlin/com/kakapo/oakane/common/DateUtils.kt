package com.kakapo.oakane.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun Long.toDateWith(format: String): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.toFormatedString(format)
}

fun Long.intoMidnight(): Long {
    val instant = Instant.fromEpochMilliseconds(this)
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(timeZone)
    val midnightLocalDateTime = LocalDateTime(
        year = localDateTime.year,
        monthNumber = localDateTime.monthNumber,
        dayOfMonth = localDateTime.dayOfMonth,
        hour = 0,
        minute = 0,
        second = 0,
        nanosecond = 0
    )

    val midnightTime = midnightLocalDateTime.toInstant(timeZone)

    return midnightTime.toEpochMilliseconds()
}

expect fun Long.formatDateWith(pattern: String): String

expect fun LocalDateTime.toFormatedString(pattern: String): String