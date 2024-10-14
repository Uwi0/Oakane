package com.kakapo.oakane.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual fun LocalDateTime.toFormatedString(pattern: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern
    val timeInterval = this.toInstant(TimeZone.currentSystemDefault()).epochSeconds.toDouble()
    val date = NSDate(timeIntervalSinceReferenceDate = timeInterval)
    return dateFormatter.stringFromDate(date)
}