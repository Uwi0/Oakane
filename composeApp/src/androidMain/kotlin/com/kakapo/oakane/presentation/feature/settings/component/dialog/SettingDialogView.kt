package com.kakapo.oakane.presentation.feature.settings.component.dialog

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DialogSettingsView(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    val dismissDialog = {
        onEvent.invoke(SettingsEvent.ShowDialog(shown = false))
    }

    BasicAlertDialog(
        onDismissRequest = {
            onEvent.invoke(SettingsEvent.ShowDialog(shown = false))
        }
    ) {
        Surface(shape = MaterialTheme.shapes.large) {
            SelectThemeDialogContent(
                theme = state.theme,
                onClick = { theme -> onEvent.invoke(SettingsEvent.Selected(theme)) },
                onDismiss = dismissDialog,
                onConfirm = dismissDialog
            )
        }
    }
}