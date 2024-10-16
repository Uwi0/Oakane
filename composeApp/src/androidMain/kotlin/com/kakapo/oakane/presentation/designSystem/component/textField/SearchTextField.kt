package com.kakapo.oakane.presentation.designSystem.component.textField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import kotlinx.coroutines.delay

@Composable
fun SearchTextFieldView(
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    debounceTime: Long = 500L
) {
    var text by remember { mutableStateOf(value) }

    LaunchedEffect(text) {
        delay(debounceTime)
        onValueChange.invoke(text)
    }

    OutlinedTextField(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        placeholder = { Text(text = placeHolder) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }
    )
}