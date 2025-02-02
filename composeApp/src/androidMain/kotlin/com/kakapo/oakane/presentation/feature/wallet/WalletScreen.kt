package com.kakapo.oakane.presentation.feature.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.SyncAlt
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletLogItem
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.goal.component.GoalSavingItemView
import com.kakapo.oakane.presentation.feature.wallet.component.TransferLogItemView
import com.kakapo.oakane.presentation.feature.wallet.component.WalletDetailItemView
import com.kakapo.oakane.presentation.feature.wallet.component.dialog.WalletDialogView
import com.kakapo.oakane.presentation.ui.component.item.CardNoteView
import com.kakapo.oakane.presentation.ui.component.item.TransactionItemView
import com.kakapo.oakane.presentation.viewModel.wallet.WalletDialogContent
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
            when (effect) {
                WalletEffect.NavigateBack -> navigateBack.invoke()
                is WalletEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    WalletScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.dialogVisible) {
        WalletDialogView(uiState = uiState, onEvent = viewModel::handleEvent)
    }
}

@Composable
private fun WalletScreen(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Scaffold(
        topBar = { WalletScreenTopAppBar(onEvent = onEvent) },
        content = { paddingValues ->
            WalletContentView(
                modifier = Modifier
                    .padding(paddingValues),
                uiState = uiState,
                onEvent = onEvent
            )
        },
        floatingActionButton = { WalletFabButtonView(onEvent = onEvent) }
    )
}

@Composable
private fun WalletScreenTopAppBar(onEvent: (WalletEvent) -> Unit) {
    CustomNavigationTopAppBarView(
        title = "Wallet",
        actions = {
            CustomIconButton(icon = Icons.Outlined.Edit) { onEvent.invoke(WalletEvent.EditWallet) }
            CustomIconButton(
                icon = Icons.Outlined.Delete,
                onClick = { onEvent.invoke(WalletEvent.ShowDialog(WalletDialogContent.DeleteWallet, true))}
            )
        },
        onNavigateBack = { onEvent.invoke(WalletEvent.NavigateBack) }
    )
}

@Composable
private fun WalletContentView(
    modifier: Modifier = Modifier,
    uiState: WalletState,
    onEvent: (WalletEvent) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        WalletTopContentView(uiState = uiState, onEvent = onEvent)
        LogsView(uiState = uiState)
    }
}

@Composable
private fun WalletTopContentView(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WalletDetailItemView(item = uiState.wallet)
        CardNoteView(note = "This is a note")
        FilterLogView(uiState = uiState, onEvent = onEvent)
    }
}

@Composable
private fun FilterLogView(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Wallet History", style = MaterialTheme.typography.titleMedium)
        FilterLogComponent(uiState = uiState, onEvent = onEvent)
    }
}

@Composable
private fun FilterLogComponent(uiState: WalletState, onEvent: (WalletEvent) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextFieldView(
            modifier = Modifier.weight(1f),
            value = uiState.searchQuery,
            onValueChange = { onEvent(WalletEvent.SearchLog(it)) },
            placeholder = "Search log"
        )
        CustomIconButton(
            icon = Icons.Outlined.FilterList,
            onClick = {}
        )
    }
}

@Composable
private fun LogsView(uiState: WalletState) {
    val currency = uiState.wallet.currency
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.filteredLogItems, key = { log -> log.uniqueId }) { log ->
            LogItemView(log, currency)
        }
    }
}

@Composable
private fun LogItemView(
    log: WalletLogItem<*>,
    currency: Currency
) {
    when (log) {
        is WalletLogItem.TransactionLogItem -> TransactionItemView(log.data)
        is WalletLogItem.WalletTransferLogItem -> TransferLogItemView(log)
        is WalletLogItem.GoalSavingLogItem -> GoalSavingItemView(log.data, currency)
    }
}

@Composable
private fun WalletFabButtonView(onEvent: (WalletEvent) -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onEvent.invoke(WalletEvent.ShowDialog(WalletDialogContent.MoveBalance, true)) },
        icon = { Icon(imageVector = Icons.Outlined.SyncAlt, contentDescription = null) },
        text = { Text(text = "Move Balance") }
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