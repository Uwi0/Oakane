package com.kakapo.oakane.presentation.designSystem.component.textField.currency

import androidx.compose.ui.text.input.TextFieldValue
import java.text.DecimalFormatSymbols


internal class InputSanitizer(
    private val currencySymbol: String,
    private val decimalFormatSymbols: DecimalFormatSymbols
) {

    fun sanitize(input: TextFieldValue): TextFieldValue {
        var sb: StringBuilder? = null
        val inputText = input.text

        var charIndex = 0
        var indexIntoPossibleCurrencySymbolMatch = 0

        while (charIndex >= 0 && charIndex < (sb?.length ?: inputText.length)) {
            val result = testCharacter(
                sb?.get(charIndex) ?: inputText[charIndex],
                indexIntoPossibleCurrencySymbolMatch
            )

            when (result) {
                CharacterTestResult.NOT_ALLOWED -> {
                    sb = onNotAllowed(sb, inputText, charIndex, indexIntoPossibleCurrencySymbolMatch)
                    charIndex = sb.length - 1
                }

                CharacterTestResult.ALLOWED -> {
                    if (indexIntoPossibleCurrencySymbolMatch != 0) {
                        sb = deletePartialCurrencySymbolMatches(
                            sb, inputText, charIndex - 1, indexIntoPossibleCurrencySymbolMatch
                        )
                        indexIntoPossibleCurrencySymbolMatch = 0
                    }
                    charIndex++
                }

                CharacterTestResult.ALLOWED_CURRENCY_SYMBOL -> {
                    indexIntoPossibleCurrencySymbolMatch = 0
                    charIndex++
                }

                CharacterTestResult.POSSIBLE_CURRENCY_SYMBOL_MATCH -> {
                    if (charIndex == (sb?.length ?: inputText.length) - 1) {
                        sb = onNotAllowed(sb, inputText, charIndex, indexIntoPossibleCurrencySymbolMatch)
                        charIndex = sb.length - 1
                    } else {
                        charIndex++
                        indexIntoPossibleCurrencySymbolMatch++
                    }
                }
            }
        }

        return if (sb != null) {
            input.copy(text = sb.toString())
        } else {
            input
        }
    }

    private fun onNotAllowed(
        sb: StringBuilder?,
        inputText: String,
        charIndex: Int,
        matchLength: Int
    ): StringBuilder {
        var mutableSb = sb ?: StringBuilder(inputText)

        if (charIndex >= 0) {
            mutableSb.deleteCharAt(charIndex)
        }

        deletePartialCurrencySymbolMatches(mutableSb, inputText, charIndex - 1, matchLength)

        return mutableSb
    }

    private fun deletePartialCurrencySymbolMatches(
        sb: StringBuilder?,
        inputText: String,
        matchEndIndex: Int,
        matchLength: Int
    ): StringBuilder {
        var mutableSb = sb ?: StringBuilder(inputText)

        var deletionIndex = matchEndIndex
        repeat(matchLength) {
            if (deletionIndex >= 0) {
                mutableSb.deleteCharAt(deletionIndex)
                deletionIndex--
            }
        }
        return mutableSb
    }

    private fun testCharacter(
        c: Char,
        indexIntoPossibleCurrencySymbolMatch: Int
    ): CharacterTestResult {
        if (c.isDigit()
            || c == decimalFormatSymbols.decimalSeparator
            || c == decimalFormatSymbols.groupingSeparator
        ) {
            return CharacterTestResult.ALLOWED
        }

        if (indexIntoPossibleCurrencySymbolMatch < currencySymbol.length) {
            if (c == currencySymbol[indexIntoPossibleCurrencySymbolMatch]) {
                return if (indexIntoPossibleCurrencySymbolMatch == currencySymbol.length - 1) {
                    CharacterTestResult.ALLOWED_CURRENCY_SYMBOL
                } else {
                    CharacterTestResult.POSSIBLE_CURRENCY_SYMBOL_MATCH
                }
            }
        }

        return CharacterTestResult.NOT_ALLOWED
    }

    private enum class CharacterTestResult {
        NOT_ALLOWED,
        ALLOWED,
        ALLOWED_CURRENCY_SYMBOL,
        POSSIBLE_CURRENCY_SYMBOL_MATCH
    }
}
