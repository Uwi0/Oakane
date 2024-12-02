package com.kakapo.oakane.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone
import platform.Foundation.timeIntervalSinceDate
import kotlin.math.absoluteValue

actual fun LocalDateTime.toFormatedString(pattern: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern
    val timeInterval = this.toInstant(TimeZone.currentSystemDefault()).epochSeconds.toDouble()
    val date = NSDate(timeIntervalSinceReferenceDate = timeInterval)
    return dateFormatter.stringFromDate(date)
}

actual fun Long.formatDateWith(pattern: String): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = pattern
        timeZone = NSTimeZone.localTimeZone
    }

    val timeOffsetFrom1970To2001 = 978307200.0
    val secondsSince2001 = (this / 1000.0) - timeOffsetFrom1970To2001
    val date = NSDate(timeIntervalSinceReferenceDate = secondsSince2001)

    return formatter.stringFromDate(date)
}

actual fun Long.daysBetween(otherDate: Long): Long {
    val thisDate = NSDate(this / 1000.0)
    val otherNsDate = NSDate(otherDate / 1000.0)
    val differenceInSeconds = thisDate.timeIntervalSinceDate(otherNsDate).absoluteValue
    return (differenceInSeconds / (24 * 60 * 60)).toLong()
}