package com.kakapo.model

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.localeWithLocaleIdentifier

actual fun Double.toFormatCurrency(currency: Currency): String {
    val identifier = "${currency.languageCode}_${currency.countryCode}"
    val formatter = NSNumberFormatter()
    val number = NSNumber(this)
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    formatter.locale = NSLocale.localeWithLocaleIdentifier(identifier)
    return formatter.stringFromNumber(number) ?: "0"
}

actual fun Double.toFormatNumber(currency: Currency): String {
    val formatter: NSNumberFormatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        groupingSeparator = "."
        decimalSeparator = ","
        maximumFractionDigits = 0u
    }

    return formatter.stringFromNumber(NSNumber(this)) ?: "0"
}