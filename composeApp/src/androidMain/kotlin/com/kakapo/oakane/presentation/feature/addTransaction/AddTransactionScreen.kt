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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.common.toDateWith
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomNumberOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddTransactionRoute(transactionId: Long, navigateBack: () -> Unit) {
    val viewmodel = koinViewModel<AddTransactionViewModel>()
    val transaction by viewmodel.transaction.collectAsStateWithLifecycle()
    val state = rememberAddTransactionState(
        transactionId = transactionId,
        categories = dummyCategories
    )

    LaunchedEffect(Unit) {
        viewmodel.initializeData(transactionId)
    }

    LaunchedEffect(transaction) {
        if (transactionId != 0L) {
            state.initializeData(transaction)
        }
    }
    val onEvent: (AddTransactionUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
            OnSaveTransaction -> {
                val transactionParam = state.getTransaction()
                viewmodel.onClickButton(transactionParam, transactionId)
                navigateBack.invoke()
            }
        }
    }

    AddTransactionScreen(state = state, onEvent = onEvent)

    if (state.isDatePickerDialogShown) {
        CustomDatePickerDialog(
            initialValue = state.selectedDate,
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
                title = state.screenTitle,
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
                    value = state.selectedDate.toDateWith(format = "dd MMM yyyy"),
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
                    onClick = { onEvent.invoke(OnSaveTransaction) },
                    content = {
                        Text(text = state.buttonTitle)
                    }
                )
            }
        }
    )
}

