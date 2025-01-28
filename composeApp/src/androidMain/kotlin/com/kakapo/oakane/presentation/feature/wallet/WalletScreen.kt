package com.kakapo.oakane.presentation.feature.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.wallet.content.WalletDetailItemView
import com.kakapo.oakane.presentation.ui.component.item.CardNoteView
import com.kakapo.oakane.presentation.viewModel.wallet.WalletEffect
import com.kakapo.oakane.presentation.viewModel.wallet.WalletEvent
import com.kakapo.oakane.presentation.viewModel.wallet.WalletState
import com.kakapo.oakane.presentation.viewModel.wallet.WalletViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun WalletRoute(walletId: Long, navigateBack: () -> Unit) {
    val viewModel = koinViewModel<WalletViewModel>()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initData(walletId)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                WalletEffect.NavigateBack -> navigateBack.invoke()
                is WalletEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    WalletScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun WalletScreen(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Scaffold(
        topBar = { WalletScreenTopAppBar(onEvent = onEvent) },
        content = { paddingValues ->
            WalletContentView(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                uiState = uiState
            )
        }
    )
}

@Composable
private fun WalletContentView(
    modifier: Modifier = Modifier,
    uiState: WalletState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WalletDetailItemView(item = uiState.wallet)
        CardNoteView(note = "This is a note")
    }
}

@Composable
private fun WalletScreenTopAppBar(onEvent: (WalletEvent) -> Unit) {
    CustomNavigationTopAppBarView(
        title = "Wallet",
        actions = {
            CustomIconButton(icon = Icons.Outlined.Edit) { onEvent.invoke(WalletEvent.EditWallet) }
            CustomIconButton(icon = Icons.Outlined.Delete) { onEvent.invoke(WalletEvent.DeleteWallet) }
        },
        onNavigateBack = { onEvent.invoke(WalletEvent.NavigateBack) }
    )
}

@Preview
@Composable
private fun WalletScreenPreview() {
    AppTheme {
        WalletScreen(uiState = WalletState()) {

        }
    }
}