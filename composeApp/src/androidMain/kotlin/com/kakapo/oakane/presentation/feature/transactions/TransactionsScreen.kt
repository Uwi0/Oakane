package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.common.utils.showToast
import com.kakapo.oakane.presentation.feature.transactions.component.SwipeToDeleteTransactionView
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionBottomSheetView
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionTopAppBarView
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEffect
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsState
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsRoute(navigateBack: () -> Unit, navigateToTransaction: (Long) -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<TransactionsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { uiState.sheetShown }
    )

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                is TransactionsEffect.ToDetail -> navigateToTransaction.invoke(effect.id)
                TransactionsEffect.HideSheet -> sheetState.hide()
                TransactionsEffect.NavigateBack -> navigateBack.invoke()
                is TransactionsEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    TransactionsScreen(
        state = uiState,
        onEvent = viewModel::handleEvent
    )

    if (uiState.sheetShown) {
        TransactionBottomSheetView(
            state = uiState,
            sheetState = sheetState,
            onEvent = viewModel::handleEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsScreen(
    state: TransactionsState,
    onEvent: (TransactionsEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TransactionTopAppBarView(state = state, onEvent = onEvent)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val groupedItems = state.filteredTransactions.groupBy { it.formattedDate }
                groupedItems.entries.forEach { (date, transactions) ->
                    stickyHeader {
                        Text(date)
                    }
                    items(items = transactions, key = { item -> item.id }) { transaction ->
                        SwipeToDeleteTransactionView(transaction, onEvent = onEvent)
                    }
                }
            }
        }
    )
}

