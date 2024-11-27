package com.kakapo.oakane.common

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.localeWithLocaleIdentifier

actual fun Double.toFormatIDRCurrency(): String {
    val formatter = NSNumberFormatter()
    val number = NSNumber(this)
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    formatter.locale = NSLocale.localeWithLocaleIdentifier("id_ID")
    return formatter.stringFromNumber(number)?.toString() ?: ""

}