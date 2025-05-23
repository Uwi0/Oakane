package com.kakapo.oakane.presentation.feature.wallets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.feature.wallets.component.WalletItemView
import com.kakapo.oakane.presentation.feature.wallets.component.WalletsTopAppbarView
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEffect
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletsRoute(
    showDrawer: Boolean,
    openDrawer: () -> Unit,
    navigateBack: () -> Unit,
    navigateToWallet: (Long) -> Unit,
    navigateToCreateWallet: (Long, Boolean) -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<WalletsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData(showDrawer)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is WalletsEffect.NavigateBack -> navigateBack.invoke()
                is WalletsEffect.ShowError -> context.showToast(effect.message)
                is WalletsEffect.NavigateToWallet -> navigateToWallet.invoke(effect.id)
                WalletsEffect.OpenDrawer -> openDrawer.invoke()
                WalletsEffect.NavigateToCreateWallet -> navigateToCreateWallet.invoke(0, false)
            }
        }
    }

    WalletsScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun WalletsScreen(uiState: WalletsState, onEvent: (WalletsEvent) -> Unit) {
    Scaffold(
        topBar = {
            WalletsTopAppbarView(uiState, onEvent)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.filteredWallets, key = { it.id }) { wallet ->
                    WalletItemView(wallet, uiState.theme, onEvent)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent.invoke(WalletsEvent.NavigateToCreateWallet) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    )
}