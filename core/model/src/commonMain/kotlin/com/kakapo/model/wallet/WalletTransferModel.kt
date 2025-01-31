package com.kakapo.model.wallet

import com.kakapo.model.Currency

data class WalletTransferModel(
    val id: Long = 0,
    val walletName: String,
    val amount: Double,
    val notes: String,
    val createdAt: Long,
    val transferType: WalletTransferType,
    val currency: Currency
){
    val uniqueId: String get() {
        return "wallet_transfer$id"
    }
}

