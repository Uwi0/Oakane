package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.model.transaction.TransactionModel

data class TransactionParam(
    val title: String,
    val amount: Double,
    val type: Long,
    val category: String,
    val dateCreated: Long,
    val note: String?
) {
    fun toEntity() = TransactionEntity(
        title = title,
        amount = amount,
        type = type,
        category = category,
        dateCreated = dateCreated,
        note = note
    )
}
