package com.kakapo.oakane.presentation.designSystem.component.textField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun CustomClickableOutlinedTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    trailingIcon: ImageVector,
    value: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        readOnly = true,
        modifier = modifier,
        value = value,
        shape = MaterialTheme.shapes.medium,
        onValueChange = {},
        placeholder = {
            Text(text = placeHolder)
        },
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
            )
        },
        interactionSource = interactionSource.also { interaction ->
            LaunchedEffect(key1 = interaction) {
                interaction.interactions.collect {
                    if (it is PressInteraction.Release) {
                        onClick.invoke()
                    }
                }
            }
        }
    )
}
