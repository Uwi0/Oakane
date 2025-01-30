package com.kakapo.oakane.presentation.viewModel.wallet

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.data.model.WalletTransferParam
import com.kakapo.model.wallet.WalletItemModel
import com.kakapo.model.wallet.WalletModel
import kotlinx.datetime.Clock

data class WalletState(
    val wallet: WalletItemModel = WalletItemModel(),
    val wallets: List<WalletModel> = emptyList(),
    val moveBalanceNote: String = "",
    val dialogVisible: Boolean = false,
    val selectedWalletFrom: WalletModel = WalletModel(),
    val selectedWalletTo: WalletModel = WalletModel(),
    val movedBalance: String = ""
) {
    val selectedWalletId: Long get() = wallet.id

    private val toWalletId: Long get() {
        return if (selectedWalletTo.id == 0L) {
            wallet.id
        } else {
            selectedWalletTo.id
        }
    }

    fun asWalletTransfer() = WalletTransferParam(
        fromWalletId = selectedWalletId,
        toWalletId = toWalletId,
        amount = movedBalance.asRealCurrencyValue(),
        notes = moveBalanceNote,
        createdAt = Clock.System.now().toEpochMilliseconds()
    )
}

sealed class WalletEffect {
    data object NavigateBack : WalletEffect()
    data class ShowError(val message: String) : WalletEffect()
}

sealed class WalletEvent {
    data object NavigateBack : WalletEvent()
    data object EditWallet : WalletEvent()
    data object DeleteWallet : WalletEvent()
    data class DialogShown(val shown: Boolean) : WalletEvent()
    data class AddNote(val note: String) : WalletEvent()
    data class AddBalance(val balance: String) : WalletEvent()
    data class AddSelectedWalletFrom(val wallet: WalletModel) : WalletEvent()
    data class AddSelectedWalletTo(val wallet: WalletModel) : WalletEvent()
    data object MoveBalance: WalletEvent()
}