package com.kakapo.oakane.presentation.feature.monthlyBudget.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
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
import java.text.NumberFormat
import java.util.Locale

@Composable
internal fun BudgetTextFieldView(value: String, onValueChange: (String) -> Unit) {

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
        modifier = Modifier.fillMaxWidth(),
        value = formattedValue,
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
        placeholder = {
            Text("0")
        },
        prefix = {
            Text("Rp.")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Assignment,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        },
        shape = MaterialTheme.shapes.medium,
    )
}