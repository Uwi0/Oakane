package com.kakapo.oakane.presentation.feature.createwallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.designSystem.theme.AppTheme
import com.kakapo.oakane.presentation.feature.createwallet.component.CreateWalletSheet
import com.kakapo.oakane.presentation.model.colorsSelector
import com.kakapo.oakane.presentation.ui.component.HorizontalColorSelectorView
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import com.kakapo.oakane.presentation.ui.component.SelectedIconView
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletEffect
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletEvent
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletSheetContent
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletState
import com.kakapo.oakane.presentation.viewModel.createWallet.CreateWalletViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateWalletRoute(
    walletId: Long,
    isFromOnboarding: Boolean,
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<CreateWalletViewModel>()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initData(walletId)
    }

    val onSuccess: () -> Unit = {
        if (isFromOnboarding) {
            navigateToHome.invoke()
        } else {
            navigateBack.invoke()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                CreateWalletEffect.NavigateBack -> navigateBack.invoke()
                is CreateWalletEffect.ShowError -> context.showToast(effect.message)
                CreateWalletEffect.SuccessCreateWallet -> onSuccess.invoke()
            }
        }
    }

    LaunchedEffect(uiState.isSheetShown) {
        if (!uiState.isSheetShown) {
            sheetState.hide()
        } else {
            sheetState.show()
        }
    }

    CreateWalletScreen(state = uiState, onEvent = viewModel::handleEvent)

    if (uiState.isSheetShown) {
        CreateWalletSheet(
            sheetState = sheetState,
            state = uiState,
            onEvent = viewModel::handleEvent
        )
    }
}

@Composable
private fun CreateWalletScreen(state: CreateWalletState, onEvent: (CreateWalletEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Create Wallet",
                onNavigateBack = { onEvent.invoke(CreateWalletEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            CreateWalletContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                state = state,
                onEvent = onEvent
            )
        }
    )
}

@Composable
private fun CreateWalletContent(
    modifier: Modifier = Modifier,
    state: CreateWalletState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    val textFieldConfig = rememberCurrencyTextFieldState(
        config = CurrencyTextFieldConfig(
            currencySymbol = state.currency.symbol,
            locale = Locale(state.currency.languageCode, state.currency.countryCode),
            initialText = state.wallet.startBalance
        ),
        onChange = { onEvent.invoke(CreateWalletEvent.BalanceChanged(it)) }
    )

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CreateWalletContent(state = state, onEvent = onEvent)
        StartBalanceContent(textFieldState = textFieldConfig)
        NoteContent(state = state, onEvent = onEvent)
        ColorContent(
            state = state,
            onClickBrush = {
                onEvent.invoke(CreateWalletEvent.ShowSheet(content = CreateWalletSheetContent.Color, shown = true))
            },
            onClickColor = { hex -> onEvent.invoke(CreateWalletEvent.SelectedColor(hex)) }
        )
        Spacer(Modifier.weight(1f))
        ConfirmButtonView(
            isEditMode = state.isEditMode,
            saveWallet = { onEvent.invoke(CreateWalletEvent.SaveWallet) }
        )
    }
}

@Composable
private fun CreateWalletContent(
    state: CreateWalletState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    val selectedIcon = SelectedIconModel(
        imageFile = state.selectedImageFile,
        defaultIcon = state.selectedIconName,
        color = state.defaultColor
    )
    ColumnContent(title = "Wallet Name") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectedIconView(
                selectedIcon = selectedIcon,
                onClick = {
                    onEvent.invoke(
                        CreateWalletEvent.ShowSheet(content = CreateWalletSheetContent.Icon, shown = true)
                    )
                }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = state.walletName,
                onValueChange = { onEvent.invoke(CreateWalletEvent.WalletNameChanged(it)) },
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = "Wallet Name") },
                singleLine = true
            )
        }
    }
}

@Composable
private fun StartBalanceContent(
    textFieldState: CurrencyTextFieldState
) {
    ColumnContent(title = "Start Balance") {
        OutlinedCurrencyTextFieldView(
            state = textFieldState,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NoteContent(
    state: CreateWalletState,
    onEvent: (CreateWalletEvent) -> Unit
) {
    ColumnContent("Note") {
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.note,
            onValueChange = { onEvent.invoke(CreateWalletEvent.NoteChanged(it)) },
            placeHolder = "Note..."
        )
    }
}

@Composable
private fun ColorContent(
    state: CreateWalletState,
    onClickBrush: () -> Unit,
    onClickColor: (String) -> Unit
) {
    ColumnContent(title = "Wallet Color") {
        HorizontalColorSelectorView(
            defaultColor = state.defaultColor,
            colorsHex = colorsSelector,
            onClickBrush = onClickBrush,
            onClickColor = onClickColor
        )
    }
}

@Composable
private fun ColumnContent(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        content.invoke(this)
    }
}

@Composable
private fun ConfirmButtonView(
    isEditMode: Boolean,
    saveWallet: () -> Unit
) {
    val title = if (isEditMode) "Update" else "Create"
    CustomButton(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        onClick = saveWallet,
        content = { Text(title) }
    )
}


@Preview
@Composable
private fun CreateWalletScreenPrev() {
    AppTheme {
        CreateWalletScreen(state = CreateWalletState(), onEvent = {})
    }
}