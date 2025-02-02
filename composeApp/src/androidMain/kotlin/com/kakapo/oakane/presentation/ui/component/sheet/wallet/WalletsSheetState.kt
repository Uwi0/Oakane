package com.kakapo.oakane.presentation.ui.component.sheet.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.toColorLong
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryIconName
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.theme.colorsSelector
import com.kakapo.oakane.presentation.model.WalletSheetContent
import com.kakapo.oakane.presentation.ui.component.SelectedIconModel
import java.util.Locale

@Composable
fun rememberWalletSheetState(
    currency: Currency = Currency.IDR,
    wallet: WalletItemModel = WalletItemModel()
) = remember {
    WalletsSheetState(currency,wallet)
}

class WalletsSheetState(
    currency: Currency,
    wallet: WalletItemModel
) {

    var walletName by mutableStateOf(wallet.name)
    var sheetContent by mutableStateOf(WalletSheetContent.Create)
    var balance by mutableStateOf(wallet.startBalance)
    var selectedColor by mutableStateOf(wallet.color)
    var selectedIconName by mutableStateOf(CategoryIconName.WALLET)
    var selectedImageFile by mutableStateOf(wallet.icon)

    val isEditMode = wallet.id != 0L
    val textFieldConfig = CurrencyTextFieldConfig(
        Locale(currency.languageCode, currency.countryCode),
        initialText = wallet.startBalance,
        currencySymbol = currency.symbol
    )
    val selectedIcon = SelectedIconModel(
        imageFile = selectedImageFile,
        defaultIcon = selectedIconName,
        color = selectedColor.ifEmpty { colorsSelector.first() }.toColorLong()
    )


    val defaultColor: Long get() {
        val color = selectedColor.ifEmpty { colorsSelector.first() }
        return color.toColorLong()
    }

    fun confirmSaveWallet() {

    }

    fun onSelectedColor(hex: String) {
        selectedColor = hex
    }
}