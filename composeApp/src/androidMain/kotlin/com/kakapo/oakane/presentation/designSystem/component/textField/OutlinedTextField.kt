package com.kakapo.oakane.presentation.designSystem.component.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        value = value,
        onValueChange =onValueChange,
        placeholder = { Text(text = placeHolder) },
        label = { Text(text = placeHolder) }
    )
}

@Composable
fun CustomNumberOutlinedTextField(
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeHolder) },
        label = { Text(text = placeHolder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}