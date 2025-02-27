package com.kakapo.model.wallet

import com.kakapo.common.formatDateWith
import com.kakapo.model.Currency
import com.kakapo.model.transaction.TransactionType

data class WalletTransferModel(
    val id: Long = 0,
    val name: String = "Some Wallet",
    val amount: Double = 2000.0,
    val notes: String = "Just Note",
    val createdAt: Long = 1740620341213,
    val type: WalletTransferType = WalletTransferType.Incoming,
    val currency: Currency = Currency.IDR
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
        fun default() = WalletTransferModel()
    }
}

