package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomNumberOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView

@Composable
internal fun AddTransactionRoute(navigateBack: () -> Unit) {

    val state = rememberAddTransactionState(categories = dummyCategories)

    val onEvent: (AddTransactionUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    AddTransactionScreen(state = state, onEvent = onEvent)
}

@Composable
private fun AddTransactionScreen(
    state: AddTransactionState,
    onEvent: (AddTransactionUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Add Transaction",
                onNavigateBack = { onEvent.invoke(OnNavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Title",
                    value = state.title,
                    onValueChange = state::onTitleChanged
                )
                CustomNumberOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Amount",
                    value = state.amount,
                    onValueChange = state::onAmountChanged
                )
                CustomDropdownMenu(
                    options = state.transactionOptions,
                    expanded = state.isTypeExpanded,
                    onExpandedChange = state::toggleTransactionType,
                    value = state.selectedTransactionType,
                    label = "Transaction Type",
                    onDismissRequest = { state.toggleTransactionType(false) },
                    onClick = { state.onTransactionTypeChanged(it) }
                )
                CustomDropdownMenu(
                    options = state.categories,
                    expanded = state.isCategoryExpanded,
                    onExpandedChange = state::toggleCategoryExpanded,
                    value = state.selectedCategory,
                    label = "Category",
                    onDismissRequest = { state.toggleCategoryExpanded(false) },
                    onClick = { state.onCategoryChanged(it) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Note",
                    value = state.note,
                    onValueChange = state::onNoteChanged
                )
            }
        }
    )
}

