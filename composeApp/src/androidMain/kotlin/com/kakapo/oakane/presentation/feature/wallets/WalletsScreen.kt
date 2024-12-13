package com.kakapo.oakane.presentation.feature.wallets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun WalletsRoute() {
    WalletsScreen()
}

@Composable
private fun WalletsScreen() {
    Scaffold(
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text("Hello world")
            }
        }
    )
}