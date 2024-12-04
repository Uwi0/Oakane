package com.kakapo.oakane.common

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
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

fun getEndOfMonthUnixTime(): Long {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val nextMonth = currentDate.plus(1, DateTimeUnit.MONTH)
    val firstDayOfNextMonth = LocalDate(nextMonth.year, nextMonth.month, 1)
    val lastDayOfCurrentMonth = firstDayOfNextMonth.minus(1, DateTimeUnit.DAY)
    val endOfMonthDateTime = lastDayOfCurrentMonth.atTime(23, 59, 59)
    val unixTime = endOfMonthDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    return unixTime
}

expect fun Long.formatDateWith(pattern: String): String

expect fun LocalDateTime.toFormatedString(pattern: String): String

expect fun Long.daysBetween(otherDate: Long): Long