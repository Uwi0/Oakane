package com.kakapo.oakane.data.database.model

import com.kakapo.WalletTable

data class WalletEntity(
    val id: Long,
    val name: String,
    val balance: Double,
    val createdAt: Long,
    val updateAt: Long
)

fun WalletTable.toWalletEntity(): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance,
        createdAt = createdAt,
        updateAt = updateAt
    )
}
