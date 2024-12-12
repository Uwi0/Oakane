package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.asTransactionType

data class TransactionParam(
    val id: Long = 0,
    val walletId: Long = 0,
    val title: String,
    val amount: Double,
    val type: Long,
    val category: CategoryModel,
    val dateCreated: Long,
    val note: String?
) {
    val walletBalance: Double get() {
        return if(type == 0L) amount else -amount
    }

    fun toEntity() = TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        type = type,
        category = category.toCategoryEntity(),
        dateCreated = dateCreated,
        note = note
    )
}

fun TransactionEntity.toModel() = TransactionModel(
    id = id,
    title = title,
    type = type.asTransactionType(),
    dateCreated = dateCreated,
    category = category.toCategoryModel(),
    amount = amount,
    note = note ?: ""
)
