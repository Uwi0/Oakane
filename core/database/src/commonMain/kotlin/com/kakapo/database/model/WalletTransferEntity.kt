package com.kakapo.database.model

data class WalletTransferEntity(
    val id: Long = 0,
    val fromWalletId: Long,
    val toWalletId: Long,
    val amount: Double,
    val notes: String,
    val createdAt: Long,
)
