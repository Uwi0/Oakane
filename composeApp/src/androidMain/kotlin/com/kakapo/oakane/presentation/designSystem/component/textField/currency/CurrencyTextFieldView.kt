package com.kakapo.oakane.presentation.designSystem.component.textField.currency

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedCurrencyTextFieldView(
    state: CurrencyTextFieldState,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    maxLines: Int = 1,
    label: @Composable() (() -> Unit)? = null,
    errorColor: Color = LocalTextStyle.current.color,
    errorText: String? = null,
    isError: Boolean = state.isError
) {

    Column(
        horizontalAlignment = Alignment.End
    ) {
        OutlinedTextField(
            value = state.textFieldState,
            label = label,
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            onValueChange = { newValue -> state.onValueChange(newValue) },
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            isError = isError,
            prefix = { Text(state.config.currencySymbol) },
            placeholder = { Text("0") }
        )

        AnimatedVisibility(visible = state.isError && errorText?.isNotEmpty() == true) {
            Text(
                text = errorText ?: "",
                modifier = Modifier.padding(end = 10.dp),
                fontSize = 16.sp,
                color = if (state.isError) errorColor else LocalTextStyle.current.color
            )
        }
    }
}