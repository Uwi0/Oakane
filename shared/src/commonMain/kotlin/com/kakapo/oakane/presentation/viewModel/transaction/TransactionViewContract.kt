package com.kakapo.oakane.presentation.viewModel.transaction

import com.kakapo.oakane.model.transaction.TransactionModel
import kotlin.native.ObjCName

@ObjCName("TransactionStateKt")
data class TransactionState(
    val transaction: TransactionModel = TransactionModel(),
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
}