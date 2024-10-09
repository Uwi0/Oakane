package com.kakapo.oakane.presentation.designSystem.component.topAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationTopAppBarView(title: String, onNavigateBack: () -> Unit) {
    Surface(shadowElevation = 2.dp, tonalElevation = 2.dp) {
        TopAppBar(
            title = {
                Text(text = title)
            },
            navigationIcon = {
                CustomIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = onNavigateBack
                )
            }
        )
    }

}