package com.kakapo.oakane.presentation.feature.settings.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.model.system.Theme

@Composable
internal fun SelectThemeDialogContent(
    theme: Theme,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text("Choose Theme", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(16.dp))
        ThemeSelection(theme, onClick)
        Spacer(Modifier.size(16.dp))
        ButtonAction(modifier = Modifier.align(Alignment.End),onDismiss, onConfirm)
    }
}

@Composable
private fun ThemeSelection(selectedTheme: Theme, onClick: (Int) -> Unit) {
    Column {
        Theme.entries.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isSelected = selectedTheme.ordinal == theme.ordinal
                RadioButton(
                    selected = isSelected,
                    onClick = { onClick(theme.ordinal) }
                )
                Text(theme.asString())
            }
        }
    }
}

@Composable
private fun ButtonAction(modifier: Modifier, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
        TextButton(onClick = onConfirm) {
            Text("Confirm")
        }
    }
}

fun Theme.asString() = when (this) {
    Theme.System -> "System"
    Theme.Light -> "Light"
    Theme.Dark -> "Dark"
}