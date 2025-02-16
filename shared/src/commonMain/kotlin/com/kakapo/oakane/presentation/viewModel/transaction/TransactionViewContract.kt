package com.kakapo.oakane.presentation.viewModel.transaction

import com.kakapo.model.Currency
import com.kakapo.model.system.Theme
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.wallet.WalletModel
import kotlin.native.ObjCName

@ObjCName("TransactionStateKt")
data class TransactionState(
    val transaction: TransactionModel = TransactionModel(),
    val wallet: WalletModel = WalletModel(),
    val currency: Currency = Currency.IDR,
    val dialogShown: Boolean = false,
    val theme: Theme = Theme.System
)

sealed class TransactionEffect{
    data object NavigateBack: TransactionEffect()
    data class EditTransactionBy(val id: Long): TransactionEffect()
    data class ShowError(val message: String): TransactionEffect()
}

sealed class TransactionEvent{
    data object NavigateBack: TransactionEvent()
    data object EditTransaction: TransactionEvent()
    data object DeleteTransaction: TransactionEvent()
    data class DialogShown(val shown: Boolean): TransactionEvent()
}