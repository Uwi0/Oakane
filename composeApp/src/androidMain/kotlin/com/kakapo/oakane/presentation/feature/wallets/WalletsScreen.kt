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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.feature.wallets.component.ConfirmDeleteWalletDialogView
import com.kakapo.oakane.presentation.feature.wallets.component.WalletItemView
import com.kakapo.oakane.presentation.feature.wallets.component.WalletsTopAppbarView
import com.kakapo.oakane.presentation.feature.wallets.component.sheet.WalletsSheet
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEffect
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsEvent
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsState
import com.kakapo.oakane.presentation.viewModel.wallets.WalletsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletsRoute(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<WalletsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { uiState.sheetContent == WalletSheetContent.Create }
    )

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is WalletsEffect.NavigateBack -> navigateBack.invoke()
                WalletsEffect.DismissBottomSheet -> sheetState.hide()
                is WalletsEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    WalletsScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.isSheetShown) {
        WalletsSheet(
            sheetState = sheetState,
            uiState = uiState,
            onEvent = viewModel::handleEvent
        )
    }

    if (uiState.dialogShown){
        ConfirmDeleteWalletDialogView(onEvent = viewModel::handleEvent)
    }
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
                items(uiState.wallets, key = { it.id }) { wallet ->
                    WalletItemView(wallet, onEvent)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent.invoke(WalletsEvent.IsSheet(shown = true)) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    )
}