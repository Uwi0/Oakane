package com.kakapo.oakane.presentation.feature.settings.component.dialog

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.viewModel.settings.SettingsDialogContent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DialogThemeView(
    theme: Theme,
    onEvent: (SettingsEvent) -> Unit,
) {
    val dismissContent = SettingsDialogContent.Theme
    val dismissDialog = {
        onEvent.invoke(SettingsEvent.ShowDialog(content = dismissContent, shown = false))
    }

    BasicAlertDialog(
        onDismissRequest = {
            onEvent.invoke(SettingsEvent.ShowDialog(content = dismissContent, shown = false))
        }
    ) {
        Surface(shape = MaterialTheme.shapes.large) {
            SelectThemeDialogContent(
                theme = theme,
                onClick = { theme -> onEvent.invoke(SettingsEvent.Selected(theme)) },
                onDismiss = dismissDialog,
                onConfirm = dismissDialog
            )
        }
    }
}