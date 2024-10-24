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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.feature.transactions.component.SwipeToDeleteTransactionView
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionBottomSheetView
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionTopAppBarView
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsRoute(navigateBack: () -> Unit, navigateToTransaction: (Long) -> Unit) {
    val viewModel = koinViewModel<TransactionsViewModel>()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val state = rememberTransactionUiState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { state.bottomSheetShown }
    )
    val onEvent: (TransactionsUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
            OnDismissBottomSheet -> scope.launch {
                sheetState.hide()
                state.hideBottomSheet()
            }

            is OnDelete -> viewModel.deleteTransaction(it.item)
            is OnNavigateToTransaction -> navigateToTransaction.invoke(it.id)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    //TODO: add filter by category
    LaunchedEffect(
        state.searchQuery,
        state.selectedType,
        state.selectedDate
    ) {
        viewModel.filterTransactionsBy(
            state.searchQuery,
            state.selectedType,
            state.selectedDate
        )
    }

    TransactionsScreen(state = state, transactions = transactions, onEvent = onEvent)
    if (state.bottomSheetShown) {
        TransactionBottomSheetView(state = state, sheetState = sheetState, onEvent = onEvent)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionsScreen(
    state: TransactionsUiState,
    transactions: List<TransactionModel>,
    onEvent: (TransactionsUiEvent) -> Unit
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
                val groupedItems = transactions.groupBy { it.formattedDate }
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

