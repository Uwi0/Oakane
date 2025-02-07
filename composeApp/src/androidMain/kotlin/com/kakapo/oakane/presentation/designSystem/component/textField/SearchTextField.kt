package com.kakapo.oakane.presentation.designSystem.component.textField

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.delay

@Composable
fun SearchTextFieldView(
    modifier: Modifier = Modifier,
    placeholder: String = "",
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
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onValueChange.invoke(text) }),
        singleLine = true,
        maxLines = 1,
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search Text Field"
                    )
                }
            }
        }
    )
}