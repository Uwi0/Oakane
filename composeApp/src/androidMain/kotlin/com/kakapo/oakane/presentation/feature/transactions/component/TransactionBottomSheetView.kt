package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsContentSheet
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionBottomSheetView(
    state: TransactionsState,
    sheetState: SheetState,
    onEvent: (TransactionsEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(TransactionsEvent.ShowSheet(false)) },
        content = {
            when(state.sheetContent) {
                TransactionsContentSheet.Type -> FilterTypeView(state, onEvent)
                TransactionsContentSheet.Date -> FilterDateView(onEvent)
                TransactionsContentSheet.Category -> FilterCategoryView(state, onEvent)
            }
        }
    )
}