package com.kakapo.oakane.presentation.feature.wallets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.feature.wallets.component.WalletItemView
import com.kakapo.oakane.presentation.feature.wallets.component.WalletsTopAppbarView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun WalletsRoute() {
    val viewModel = koinViewModel<WalletsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WalletsScreen(uiState = uiState)
}

@Composable
private fun WalletsScreen(uiState: WalletsState) {
    Scaffold(
        topBar = {
            WalletsTopAppbarView()
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.wallets){ wallet ->
                    WalletItemView(wallet)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    )
}