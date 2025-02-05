package com.kakapo.model.wallet

import com.kakapo.common.formatDateWith
import com.kakapo.model.Currency
import com.kakapo.model.transaction.TransactionType

data class WalletTransferModel(
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val notes: String,
    val createdAt: Long,
    val type: WalletTransferType,
    val currency: Currency
){
    val uniqueId: String get() {
        return "wallet_transfer$id"
    }

    val formattedDate: String get() {
        return createdAt.formatDateWith(FORMAT_DATE)
    }

    val transactionType: TransactionType
        get() {
        return when (type) {
            WalletTransferType.Outgoing -> TransactionType.Expense
            WalletTransferType.Incoming -> TransactionType.Income
        }
    }

    companion object {
        private const val FORMAT_DATE = "dd MMM yyyy"
    }
}

