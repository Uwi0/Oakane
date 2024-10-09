package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomNumberOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog

@Composable
internal fun AddTransactionRoute(navigateBack: () -> Unit) {

    val state = rememberAddTransactionState(categories = dummyCategories)

    val onEvent: (AddTransactionUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    AddTransactionScreen(state = state, onEvent = onEvent)

    if (state.isDatePickerDialogShown) {
        CustomDatePickerDialog(
            onDismiss = { state.toggleDatePickerDialog(false) },
            onConfirm = state::onSelectedDate
        )
    }
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
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Date",
                    trailingIcon = Icons.Default.Today,
                    value = state.selectedDate.toString(),
                    onClick = { state.toggleDatePickerDialog(true) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Note",
                    value = state.note,
                    onValueChange = state::onNoteChanged
                )
                Spacer(Modifier.weight(1f))
                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = { /*TODO*/ },
                    content = {
                        Text(text = "Add Transaction")
                    }
                )
            }
        }
    )
}

