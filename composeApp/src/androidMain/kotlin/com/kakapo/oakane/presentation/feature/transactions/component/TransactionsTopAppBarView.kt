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
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationMenuTopAppBarView
import com.kakapo.oakane.presentation.ui.component.ColumnWrapper
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsContentSheet
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsEvent
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsState

@Composable
internal fun TransactionTopAppBarView(
    state: TransactionsState,
    onEvent: (TransactionsEvent) -> Unit
) {
    ColumnWrapper(modifier = Modifier.padding(bottom = 8.dp), shapes = RectangleShape, theme = state.theme) {
        CustomNavigationMenuTopAppBarView(
            title = "Transactions",
            showDrawer = state.showDrawer,
            shadowElevation = 0.dp,
            onNavigateBack = {
                onEvent.invoke(TransactionsEvent.NavigateBack)
            },
            openMenu = {
                onEvent.invoke(TransactionsEvent.OpenDrawer)
            }
        )
        SearchTextFieldView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            placeholder = "Search Transaction...",
            value = state.searchQuery,
            onValueChange = { query -> onEvent.invoke(TransactionsEvent.FilterBy(query)) }
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChipSelector(
                title = state.typeTile,
                isSelected = state.selectedType != null,
                onClick = {
                    if (state.selectedType != null)
                        onEvent.invoke(TransactionsEvent.FilterByType(null))
                    else
                        onEvent.invoke(TransactionsEvent.ShowSheet(TransactionsContentSheet.Type))
                }
            )
            ChipSelector(
                title = "By Date",
                isSelected = state.selectedDate != 0L,
                onClick = {
                    if (state.selectedDate != 0L)
                        onEvent.invoke(TransactionsEvent.FilterByDate(0L))
                    else
                    onEvent.invoke(TransactionsEvent.ShowSheet(TransactionsContentSheet.Date))
                }
            )
            ChipSelector(
                title = "By Category",
                isSelected = state.selectedCategory != null,
                onClick = {
                    if (state.selectedCategory != null)
                        onEvent.invoke(TransactionsEvent.FilterByCategory(null))
                    else
                    onEvent.invoke(TransactionsEvent.ShowSheet(TransactionsContentSheet.Category))
                }
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