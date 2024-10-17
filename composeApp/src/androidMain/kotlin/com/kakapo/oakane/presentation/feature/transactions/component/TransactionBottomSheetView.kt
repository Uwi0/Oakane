package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kakapo.oakane.presentation.feature.transactions.OnDismissBottomSheet
import com.kakapo.oakane.presentation.feature.transactions.TransactionBottomSheet
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiEvent
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionBottomSheetView(
    state: TransactionsUiState,
    sheetState: SheetState,
    onEvent: (TransactionsUiEvent) -> Unit
){
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(OnDismissBottomSheet) },
        content = {
            when(state.bottomSheetContent){
                TransactionBottomSheet.Type -> FilterTypeView(state = state)
                TransactionBottomSheet.Date -> Text("Date")
                TransactionBottomSheet.Category -> Text("Category")
            }
        }
    )
}