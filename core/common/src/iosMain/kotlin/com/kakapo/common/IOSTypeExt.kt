package com.kakapo.common

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.localeWithLocaleIdentifier



actual fun Double.toFormatIDR(): String {
    val formatter = NSNumberFormatter()
    val number = NSNumber(this)
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    return formatter.stringFromNumber(number) ?: "0"
}