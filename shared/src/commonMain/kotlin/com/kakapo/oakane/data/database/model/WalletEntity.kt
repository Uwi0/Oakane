package com.kakapo.oakane.data.database.model

data class WalletEntity(
    val id: Long,
    val name: String,
    val balance: Double,
    val createdAt: Long,
    val updateAt: Long
)
