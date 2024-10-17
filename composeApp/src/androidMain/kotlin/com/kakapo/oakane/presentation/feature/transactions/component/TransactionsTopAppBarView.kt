package com.kakapo.oakane.presentation.feature.transactions.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.transactions.OnNavigateBack
import com.kakapo.oakane.presentation.feature.transactions.TransactionBottomSheet
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiEvent
import com.kakapo.oakane.presentation.feature.transactions.TransactionsUiState
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper

@Composable
internal fun TransactionTopAppBarView(
    state: TransactionsUiState,
    onEvent: (TransactionsUiEvent) -> Unit
) {
    ColumnWrapper(modifier = Modifier.padding(bottom = 8.dp), shapes = RectangleShape) {
        CustomNavigationTopAppBarView(
            title = "Transactions",
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            onNavigateBack = {
                onEvent.invoke(OnNavigateBack)
            }
        )
        SearchTextFieldView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            placeHolder = "Search Transaction...",
            value = state.searchQuery,
            onValueChange = state::onChangedQuery
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChipSelector(
                title = "By Type",
                isSelected = true,
                onClick = { state.showBottomSheet(TransactionBottomSheet.Type) }
            )
            ChipSelector(
                title = "By Date",
                isSelected = false,
                onClick = { state.showBottomSheet(TransactionBottomSheet.Date) }
            )
            ChipSelector(
                title = "By Category",
                isSelected = false,
                onClick = { state.showBottomSheet(TransactionBottomSheet.Category) }
            )
        }
    }
}

@Composable
private fun ChipSelector(title: String, isSelected: Boolean, onClick: () -> Unit) {
    val icon = if (isSelected) Icons.Default.Close else Icons.Default.ArrowDropDown
    InputChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(text = title) },
        trailingIcon = { Icon(imageVector = icon, contentDescription = "") }
    )
}