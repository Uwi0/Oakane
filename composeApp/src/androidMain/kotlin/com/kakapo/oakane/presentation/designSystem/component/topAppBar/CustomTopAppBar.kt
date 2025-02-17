package com.kakapo.oakane.presentation.designSystem.component.topAppBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationTopAppBarView(
    title: String,
    shadowElevation: Dp = 2.dp,
    tonalElevation: Dp = 0.dp,
    actions: @Composable RowScope.() -> Unit = {},
    onNavigateBack: () -> Unit
) {
    Surface(shadowElevation = shadowElevation, tonalElevation = tonalElevation) {
        TopAppBar(
            title = {
                Text(text = title)
            },
            navigationIcon = {
                CustomIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = onNavigateBack
                )
            },
            actions = actions
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationMenuTopAppBarView(
    title: String,
    showDrawer: Boolean,
    shadowElevation: Dp = 2.dp,
    tonalElevation: Dp = 0.dp,
    actions: @Composable RowScope.() -> Unit = {},
    openMenu: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val icon = if (showDrawer) Icons.Default.Menu
    else Icons.AutoMirrored.Filled.ArrowBack
    Surface(shadowElevation = shadowElevation, tonalElevation = tonalElevation) {
        TopAppBar(
            title = {
                Text(text = title)
            },
            navigationIcon = {
                CustomIconButton(
                    icon = icon,
                    onClick = {
                        if (showDrawer) {
                            openMenu.invoke()
                        } else {
                            onNavigateBack.invoke()
                        }
                    }
                )
            },
            actions = actions
        )
    }
}