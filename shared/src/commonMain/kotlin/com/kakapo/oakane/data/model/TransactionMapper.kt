package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.asTransactionType

data class TransactionParam(
    val title: String,
    val amount: Double,
    val type: Long,
    val category: String,
    val dateCreated: Long,
    val note: String?
) {
    fun toEntity(id: Long? = null) = TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        type = type,
        category = category,
        dateCreated = dateCreated,
        note = note
    )
}

fun TransactionEntity.toModel() = TransactionModel(
    id = id ?: 0,
    title = title,
    type = type.asTransactionType(),
    category = category,
    dateCreated = dateCreated,
    amount = amount,
    note = note ?: ""
)
