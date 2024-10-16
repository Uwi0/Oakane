package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.transactions.OnNavigateBack
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiEvent
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiState
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun TransactionTopAppBarView(state: TransactionsUiState, onEvent: (TransactionsUiEvent) -> Unit) {
    ColumnWrapper(modifier = Modifier.padding(bottom = 8.dp),shapes = RectangleShape) {
        CustomNavigationTopAppBarView(
            title = "Transactions",
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            onNavigateBack = {
                onEvent.invoke(OnNavigateBack)
            }
        )
        SearchTextFieldView(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            value = state.searchQuery,
            onValueChange = state::onChangedQuery
        )
    }
}