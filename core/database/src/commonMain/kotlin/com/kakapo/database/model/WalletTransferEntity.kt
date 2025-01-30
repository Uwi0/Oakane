package com.kakapo.database.model

import com.kakapo.GetTransfersBy

data class WalletTransferEntity(
    val id: Long = 0,
    val fromWalletId: Long,
    val toWalletId: Long,
    val amount: Double,
    val notes: String,
    val createdAt: Long,
)

data class WalletTransferItemEntity(
    val id: Long,
    val fromWalletName: String,
    val toWalletName: String,
    val transferAmount: Double,
    val note: String,
    val createdAt: Long,
    val transferType: String
)

fun GetTransfersBy.toWalletTransferItemEntity(): WalletTransferItemEntity {
    return WalletTransferItemEntity(
        id = id,
        fromWalletName = from_wallet_name,
        toWalletName = to_wallet_name,
        transferAmount = transfer_amount,
        note = notes ?: "",
        createdAt = created_at,
        transferType = transfer_type
    )
}
