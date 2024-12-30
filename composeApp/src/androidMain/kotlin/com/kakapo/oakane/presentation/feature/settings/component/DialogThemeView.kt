package com.kakapo.oakane.presentation.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.model.system.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DialogThemeView(
    theme: Theme,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.large) {
            DialogContentThemeView(theme, onClick, onDismiss, onConfirm)
        }
    }
}

@Composable
private fun DialogContentThemeView(
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
private fun ThemeSelection(theme: Theme, onClick: (Int) -> Unit) {
    var selectedTheme by remember { mutableIntStateOf(theme.ordinal) }

    LaunchedEffect(theme) {
        selectedTheme = theme.ordinal
    }

    Column {
        Theme.entries.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val isSelected = selectedTheme == theme.ordinal
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
private fun ButtonAction(modifier: Modifier,onDismiss: () -> Unit, onConfirm: () -> Unit) {
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