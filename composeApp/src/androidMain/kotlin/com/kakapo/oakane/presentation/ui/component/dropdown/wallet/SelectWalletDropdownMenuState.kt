package com.kakapo.oakane.presentation.ui.component.dropdown.wallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.model.wallet.WalletModel
import kotlinx.coroutines.delay

@Composable
internal fun rememberSelectWalletDropdownMenuState(
    wallets: List<WalletModel>,
    selectedWalletId: Long,
    onClickedWallet: (WalletModel) -> Unit
) = remember(wallets, selectedWalletId) {
    SelectWalletDropdownMenuState(wallets, selectedWalletId, onClickedWallet)
}

class SelectWalletDropdownMenuState(
    val wallets: List<WalletModel>,
    private val selectedWalletId: Long,
    private val onClickedWallet: (WalletModel) -> Unit
) {

    var expanded: Boolean by mutableStateOf(false)
    var selectedOptionText: String by mutableStateOf("")
    var selectedWallet: WalletModel by mutableStateOf(WalletModel())
    var filteredWallet = emptyList<WalletModel>()

    private var defaultWallet = wallets.find { it.id == selectedWalletId } ?: WalletModel()
    private var userStartTyping: Boolean by mutableStateOf(false)

    init {
        selectedWallet = defaultWallet
        filteredWallet = wallets
        selectedOptionText = selectedWallet.name
    }

    fun changeSelectedOptionText(text: String) {
        selectedOptionText = text
        userStartTyping = true
    }

    suspend fun filterWallets(query: String) {
        if (userStartTyping) {
            delay(250)
            filteredWallet = filteredWallet.filter {
                it.name.contains(query, ignoreCase = true)
            }
            expanded = true
        }
    }

    fun changeExpandedState(expanded: Boolean) {
        this.expanded = expanded
    }

    fun onClicked(wallet: WalletModel) {
        selectedWallet = wallet
        selectedOptionText = wallet.name
        expanded = false
        onClickedWallet.invoke(wallet)
    }
}