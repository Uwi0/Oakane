package com.kakapo.oakane.presentation.ui.component.sheet.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.asRealCurrencyValue
import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.theme.colorsSelector
import com.kakapo.oakane.presentation.model.WalletSheetContent
import java.util.Locale

@Composable
fun rememberWalletSheetState(
    currency: Currency = Currency.IDR,
    wallet: WalletItemModel = WalletItemModel(),
    onSaveWallet: (WalletModel) -> Unit
) = remember(wallet, currency) {
    WalletsSheetState(currency,wallet, onSaveWallet = onSaveWallet)
}

class WalletsSheetState(
    private val currency: Currency,
    private val wallet: WalletItemModel,
    private val onSaveWallet: (WalletModel) -> Unit
) {

    var walletName by mutableStateOf(wallet.name)
    var sheetContent by mutableStateOf(WalletSheetContent.Create)
    var balance by mutableStateOf(wallet.startBalance)
    var selectedColor by mutableStateOf(wallet.color)
    var selectedIconName by mutableStateOf(CategoryIconName.WALLET)
    var selectedImageFile by mutableStateOf(wallet.icon)
    var note by mutableStateOf(wallet.note)

    val isEditMode = wallet.id != 0L
    val textFieldConfig = CurrencyTextFieldConfig(
        Locale(currency.languageCode, currency.countryCode),
        initialText = wallet.startBalance,
        currencySymbol = currency.symbol
    )

    val defaultColor: Long get() {
        val color = selectedColor.ifEmpty { colorsSelector.first() }
        return color.toColorLong()
    }

    fun confirmSaveWallet() {
        val icon = selectedImageFile.ifEmpty { selectedIconName.displayName }
        val walletModel = WalletModel(
            id = wallet.id,
            currency = currency,
            balance = balance.asRealCurrencyValue(),
            name = walletName,
            isDefaultIcon = selectedImageFile.isEmpty(),
            icon = icon,
            color = selectedColor.ifEmpty { "0xFF4CAF50" },
            note = note
        )
        onSaveWallet.invoke(walletModel)
    }

    fun resetContent() {
        sheetContent = WalletSheetContent.Create
    }

    fun onSelectedColor(hex: String) {
        selectedColor = hex
    }
}