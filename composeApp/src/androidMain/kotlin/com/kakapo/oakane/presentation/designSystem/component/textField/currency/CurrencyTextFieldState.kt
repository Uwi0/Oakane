package com.kakapo.oakane.presentation.designSystem.component.textField.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale
import kotlin.math.min

data class CurrencyTextFieldConfig(
    val locale: Locale = Locale.getDefault(),
    val initialText: String = "",
    val maxNoOfDecimal: Int = 2,
    val currencySymbol: String,
    val limit: Int = Int.MAX_VALUE,
)

@Composable
fun rememberCurrencyTextFieldState(
    config: CurrencyTextFieldConfig,
    onChange: ((String) -> Unit)
): CurrencyTextFieldState {
    return remember(config) {
        CurrencyTextFieldState(config = config, onChange = onChange)
    }
}

class CurrencyTextFieldState(
    val config: CurrencyTextFieldConfig,
    private val onChange: (String) -> Unit
) {
    private val decimalFormatter: DecimalFormat =
        (NumberFormat.getNumberInstance(config.locale) as DecimalFormat).apply {
            isDecimalSeparatorAlwaysShown = true
        }

    private val decimalFormatSymbols: DecimalFormatSymbols = decimalFormatter.decimalFormatSymbols

    var textFieldState by mutableStateOf(TextFieldValue(text = config.initialText))
        private set

    private var oldText = ""

    val isError: Boolean
        get() = isLimitExceeded(
            config.limit,
            textFieldState.text,
            config.currencySymbol,
            decimalFormatter
        )

    fun onValueChange(newValue: TextFieldValue) {
        val sanitizer = InputSanitizer(
            config.currencySymbol,
            decimalFormatSymbols
        )
        textFieldState = formatUserInput(
            oldText,
            sanitizer.sanitize(newValue),
            decimalFormatSymbols,
            config.maxNoOfDecimal,
            decimalFormatter
        )
        oldText = textFieldState.text
        onChange(oldText)
    }

    private fun isLimitExceeded(
        limit: Int,
        currentAmount: String,
        currencySymbol: String,
        decimalFormatter: DecimalFormat
    ): Boolean {
        val cleanedInput = currentAmount.replace(currencySymbol, "")
        if (cleanedInput.isEmpty()) {
            return false
        }
        return (decimalFormatter.parse(cleanedInput)?.toInt() ?: 0) > limit
    }

    private fun formatUserInput(
        oldText: String,
        textFieldValue: TextFieldValue,
        decimalFormatSymbols: DecimalFormatSymbols,
        maxNoOfDecimal: Int,
        decimalFormatter: DecimalFormat
    ): TextFieldValue {
        if (oldText == textFieldValue.text)
            return TextFieldValue(
                text = oldText,
                selection = TextRange(oldText.length)
            )

        var userInput = textFieldValue.text
        var finalSelection = 0

        if (
            userInput.isNotEmpty() &&
            userInput.last().toString() == "." &&
            decimalFormatSymbols.decimalSeparator.toString() != userInput.last().toString()
        ) {
            userInput = userInput.dropLast(1)
            userInput.plus(decimalFormatSymbols.decimalSeparator.toString())
        }

        if (!checkDecimalSizeExceeded(
                userInput,
                decimalFormatSymbols,
                maxNoOfDecimal
            )
        ) {

            val startLength = textFieldValue.text.length

            try {
                val parsedNumber = decimalFormatter.parse(userInput)
                decimalFormatter.applyPattern(
                    setDecimalFormatterSensitivity(
                        userInput, decimalFormatSymbols, maxNoOfDecimal
                    )
                )

                val startPoint = textFieldValue.selection.start
                userInput = decimalFormatter.format(parsedNumber)
                val finalLength = userInput.length
                val selection = startPoint + (finalLength - startLength)

                finalSelection = if (selection > 0 && selection <= userInput.length) {
                    selection
                } else {
                    userInput.length - 1
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        } else {
            finalSelection = userInput.length - 1
            userInput = userInput.substring(0, userInput.length - 1)
        }

        return TextFieldValue(
            text = userInput,
            selection = TextRange(finalSelection)
        )
    }

    private fun setDecimalFormatterSensitivity(
        userInput: String,
        decimalFormatSymbols: DecimalFormatSymbols,
        maxNoOfDecimal: Int
    ): String {
        val decimalSeparatorIndex = userInput.indexOf(decimalFormatSymbols.decimalSeparator)
        if (decimalSeparatorIndex == -1)
            return "#,##0"

        val noOfCharactersAfterDecimalPoint =
            userInput.length - decimalSeparatorIndex - 1

        val zeros = "0".repeat(
            min(
                noOfCharactersAfterDecimalPoint,
                maxNoOfDecimal
            )
        )
        return "#,##0.$zeros"
    }

    private fun checkDecimalSizeExceeded(
        input: String,
        decimalFormatSymbols: DecimalFormatSymbols,
        maxNoOfDecimal: Int
    ): Boolean {
        return (input.split(decimalFormatSymbols.decimalSeparator)
            .getOrNull(1)?.length ?: Int.MIN_VALUE) > maxNoOfDecimal
    }
}
