package com.kakapo.data.model

import com.kakapo.database.model.WalletTransferEntity
import com.kakapo.database.model.WalletTransferItemEntity
import com.kakapo.model.Currency
import com.kakapo.model.wallet.WalletTransferModel
import com.kakapo.model.wallet.WalletTransferType
import com.kakapo.model.wallet.toWalletTransferType

data class WalletTransferParam(
    val id: Long = 0,
    val fromWalletId: Long,
    val toWalletId: Long,
    val amount: Double,
    val notes: String,
    val createdAt: Long
) {

    fun toWalletTransferEntity(): WalletTransferEntity {
        return WalletTransferEntity(
            id = id,
            fromWalletId = fromWalletId,
            toWalletId = toWalletId,
            amount = amount,
            notes = notes,
            createdAt = createdAt
        )
    }
}

fun WalletTransferItemEntity.toWalletTransferModel(currency: Currency): WalletTransferModel {
    val transferType = transferType.toWalletTransferType()
    val walletName = when(transferType){
        WalletTransferType.Outgoing -> toWalletName
        WalletTransferType.Incoming -> fromWalletName
    }
    return WalletTransferModel(
        id = id,
        walletName = walletName,
        amount = transferAmount,
        notes = note,
        createdAt = createdAt,
        transferType = transferType,
        currency = currency
    )
}