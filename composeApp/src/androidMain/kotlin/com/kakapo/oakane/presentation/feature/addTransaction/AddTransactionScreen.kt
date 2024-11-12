package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.common.toDateWith
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.model.transaction.asTransactionType
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.OutlinedCurrencyTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEffect
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEvent
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionState
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddTransactionRoute(transactionId: Long, navigateBack: () -> Unit) {
    val viewModel = koinViewModel<AddTransactionViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state = rememberAddTransactionState(
        transactionId = transactionId,
        categories = dummyCategories
    )

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                AddTransactionEffect.NavigateBack -> navigateBack.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (transactionId != 0L) {
            viewModel.initializeData(transactionId)
        }
    }

    AddTransactionScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.isShowDialog) {
        CustomDatePickerDialog(
            initialValue = uiState.date,
            onDismiss = { viewModel.handleEvent(AddTransactionEvent.Dialog(shown = false)) },
            onConfirm = { viewModel.handleEvent(AddTransactionEvent.ChangeDate(it))}
        )
    }
}

@Composable
private fun AddTransactionScreen(
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit
) {
    Scaffold(
        topBar = {
            val screenTitle = if (uiState.isEditMode) "Edit Transaction" else "Add Transaction"
            CustomNavigationTopAppBarView(
                title = screenTitle,
                onNavigateBack = { onEvent.invoke(AddTransactionEvent.NavigateBack) }
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
                    value = uiState.title,
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangedTitle(it)) }
                )
                OutlinedCurrencyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Amount",
                    value = uiState.amount,
                    prefix = "Rp ",
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangedAmount(it)) }
                )
                TransactionTypeDropDown(uiState, onEvent)
//                CustomDropdownMenu(
//                    options = state.categories,
//                    expanded = state.isCategoryExpanded,
//                    onExpandedChange = state::toggleCategoryExpanded,
//                    value = state.selectedCategory,
//                    label = "Category",
//                    onDismissRequest = { state.toggleCategoryExpanded(false) },
//                    onClick = { state.onCategoryChanged(it) }
//                )
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Date",
                    trailingIcon = Icons.Default.Today,
                    value = uiState.date.toDateWith(format = "dd MMM yyyy"),
                    onClick = { onEvent.invoke(AddTransactionEvent.Dialog(shown = true)) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Note",
                    value = uiState.note,
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangeNote(it))}
                )
                Spacer(Modifier.weight(1f))
//                CustomButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    contentPadding = PaddingValues(vertical = 16.dp),
//                    onClick = { onEvent.invoke(OnSaveTransaction) },
//                    content = {
//                        Text(text = state.buttonTitle)
//                    }
//                )
            }
        }
    )
}

@Composable
private fun TransactionTypeDropDown(
    uiState: AddTransactionState,
    onEvent: (AddTransactionEvent) -> Unit
) {
    CustomDropdownMenu(
        options = TransactionType.entries.map { it.name },
        expanded = uiState.isDropdownExpanded,
        onExpandedChange = { onEvent.invoke(AddTransactionEvent.DropDownTypeIs(expanded = it)) },
        value = uiState.transactionType.name,
        label = "Transaction Type",
        onDismissRequest = { onEvent.invoke(AddTransactionEvent.DropDownTypeIs(expanded = false)) },
        onClick = { onEvent.invoke(AddTransactionEvent.ChangeType(it.asTransactionType())) }
    )
}

