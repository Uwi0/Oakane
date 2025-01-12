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
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.common.toDateWith
import com.kakapo.model.Currency
import com.kakapo.model.transaction.TransactionType
import com.kakapo.model.transaction.asTransactionType
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.menu.CustomDropdownMenu
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.addTransaction.component.SelectCategorySheet
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEffect
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionEvent
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionState
import com.kakapo.oakane.presentation.viewModel.addTransaction.AddTransactionViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTransactionRoute(transactionId: Long, navigateBack: () -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<AddTransactionViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.uiSideEffect.collect { effect ->
            when (effect) {
                AddTransactionEffect.NavigateBack -> navigateBack.invoke()
                is AddTransactionEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData(transactionId)
    }

    AddTransactionScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.isShowDialog) {
        CustomDatePickerDialog(
            initialValue = uiState.date,
            onDismiss = { viewModel.handleEvent(AddTransactionEvent.Dialog(shown = false)) },
            onConfirm = { viewModel.handleEvent(AddTransactionEvent.ChangeDate(it)) }
        )
    }

    if (uiState.sheetShown) {
        SelectCategorySheet(
            sheetState = sheetState,
            uiState = uiState,
            onEvent = viewModel::handleEvent
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
                AddTransactionCurrencyTextField(currency = uiState.currency, onEvent = onEvent)
                TransactionTypeDropDown(uiState, onEvent)
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeHolder = "Category",
                    trailingIcon = Icons.Outlined.Category,
                    value = uiState.category.name,
                    onClick = { onEvent.invoke(AddTransactionEvent.Sheet(shown = true)) }
                )
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
                    onValueChange = { onEvent.invoke(AddTransactionEvent.ChangeNote(it)) }
                )
                Spacer(Modifier.weight(1f))
                val buttonTitle = if (uiState.isEditMode) "Save" else "Add"
                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    onClick = { onEvent.invoke(AddTransactionEvent.SaveTransaction) },
                    content = {
                        Text(text = buttonTitle)
                    }
                )
            }
        }
    )
}

@Composable
private fun AddTransactionCurrencyTextField(
    currency: Currency,
    onEvent: (AddTransactionEvent) -> Unit
) {
    val textFieldConfig = CurrencyTextFieldConfig(
        Locale(currency.languageCode, currency.countryCode),
        currencySymbol = currency.symbol
    )
    val state = rememberCurrencyTextFieldState(textFieldConfig) { amount ->
        onEvent(AddTransactionEvent.ChangedAmount(amount))
    }
    OutlinedCurrencyTextFieldView(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        label = { Text("Amount") }
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

