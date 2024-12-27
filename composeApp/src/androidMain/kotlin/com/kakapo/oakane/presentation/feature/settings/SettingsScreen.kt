package com.kakapo.oakane.presentation.feature.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.ImportExport
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEffect
import com.kakapo.oakane.presentation.viewModel.settings.SettingsEvent
import com.kakapo.oakane.presentation.viewModel.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingsRoute(
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<SettingsViewModel>()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> navigateBack.invoke()
            }
        }
    }

    SettingsScreen(onEvent = viewModel::handleEvent)
}

@Composable
private fun SettingsScreen(onEvent: (SettingsEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Settings",
                onNavigateBack = { onEvent.invoke(SettingsEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsButton(
                    title = "Back Up Data",
                    icon = Icons.Outlined.Backup,
                    onClick = {}
                )
                SettingsButton(
                    title = "Import Data",
                    icon = Icons.Outlined.ImportExport,
                    onClick = {}
                )
            }
        }
    )
}

@Composable
private fun SettingsButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}