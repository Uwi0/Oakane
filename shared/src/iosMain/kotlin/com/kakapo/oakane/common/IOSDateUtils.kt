package com.kakapo.oakane.common

import co.touchlab.kermit.Logger
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

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
    val date = NSDate( timeIntervalSinceReferenceDate = this.toDouble() / 1000)
    return formatter.stringFromDate(date)
}