package com.kakapo.oakane.data.database.model

import com.kakapo.WalletTable

data class WalletEntity(
    val id: Long,
    val name: String,
    val balance: Double,
    val color: String,
    val icon: String,
    val isDefaultIcon: Long,
    val createdAt: Long,
    val updateAt: Long
) {

    fun toWalletTable(): WalletTable {
        return WalletTable(
            id = id,
            name = name,
            balance = balance,
            color = color,
            icon = icon,
            isDefaultIcon = isDefaultIcon,
            createdAt = createdAt,
            updateAt = updateAt
        )
    }

}

fun WalletTable.toWalletEntity(): WalletEntity {
    return WalletEntity(
        id = id,
        name = name,
        balance = balance,
        color = color,
        icon = icon,
        isDefaultIcon = isDefaultIcon,
        createdAt = createdAt,
        updateAt = updateAt
    )
}
