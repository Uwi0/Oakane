package com.kakapo.oakane.presentation.designSystem.component.textField

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OutlinedCurrencyTextField(
    modifier: Modifier = Modifier,
    placeHolder: String = "0",
    prefix: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {

    var formattedValue by remember { mutableStateOf(value) }

    LaunchedEffect(value) {
        val unformattedValue = value.filter { it.isDigit() }
        formattedValue = if (unformattedValue.isNotEmpty()) {
            val number = unformattedValue.toLongOrNull() ?: 0L
            NumberFormat.getInstance(Locale("in", "ID")).format(number)
        } else {
            ""
        }
    }

    OutlinedTextField(
        modifier = modifier,
        value = formattedValue,
        enabled = enabled,
        onValueChange = { newValue ->
            val unformattedValue = newValue.filter { it.isDigit() }
            val formattedText = if (unformattedValue.isNotEmpty()) {
                val number = unformattedValue.toLongOrNull() ?: 0L
                NumberFormat.getInstance(Locale("in", "ID")).format(number)
            } else {
                ""
            }
            formattedValue = formattedText
            onValueChange(unformattedValue)
        },
        shape = MaterialTheme.shapes.medium,
        textStyle = MaterialTheme.typography.labelLarge,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(placeHolder)
        },
        prefix = {
            Text(prefix)
        }
    )
}

@Composable
@Preview
private fun CurrencyTextFieldPrev() {
    ColumnWrapper (
        modifier = Modifier.fillMaxSize(),
    ) {
        OutlinedCurrencyTextField(value = "5000", placeHolder = "0",prefix = "Rp.") { }
    }
}