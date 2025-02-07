package com.kakapo.database.model

import com.kakapo.GetWallets
import com.kakapo.WalletTable
import kotlinx.serialization.Serializable

@Serializable
data class WalletEntity(
    val id: Long,
    val name: String,
    val balance: Double,
    val color: String,
    val icon: String,
    val isDefaultIcon: Long,
    val expense: Double = 0.0,
    val income: Double = 0.0,
    val note: String = "",
    val createdAt: Long,
    val updateAt: Long
)

fun WalletTable.toWalletEntity(): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance,
        color = color,
        icon = icon,
        note = note ?: "",
        isDefaultIcon = isDefaultIcon,
        createdAt = createdAt,
        updateAt = updateAt
    )
}

fun GetWallets.toWalletEntity(): WalletEntity {
    return WalletEntity(
        id = walletId,
        name = walletName,
        balance = walletBalance,
        color = walletColor,
        icon = walletIcon,
        isDefaultIcon = walletIsDefaultIcon,
        expense = totalExpense ?: 0.0,
        income = totalIncome ?: 0.0,
        note = walletNote ?: "-",
        createdAt = walletCreatedAt,
        updateAt = walletUpdateAt
    )
}
