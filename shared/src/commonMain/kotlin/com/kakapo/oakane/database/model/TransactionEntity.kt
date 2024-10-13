package com.kakapo.oakane.database.model

import com.kakapo.TransactionTable

data class TransactionEntity(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: Long,
    val category: String,
    val dateCreated: Long,
    val note: String?
)

fun TransactionTable.toEntity() = TransactionEntity(
    id = id,
    title = title,
    amount = amount,
    type = type,
    category = category,
    dateCreated = dateCreated,
    note = note
)
