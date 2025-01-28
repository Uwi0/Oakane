package com.kakapo.data.model

import com.kakapo.database.model.WalletTransferEntity

data class WalletTransferParam(
    val id: Long,
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