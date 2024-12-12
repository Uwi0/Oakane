package com.kakapo.oakane.data.database.model

import com.kakapo.GetTransactionBy
import com.kakapo.GetTransactions

data class TransactionEntity(
    val id: Long,
    val walletId: Long = 0,
    val title: String,
    val amount: Double,
    val type: Long,
    val category: CategoryEntity,
    val dateCreated: Long,
    val note: String?
)

fun GetTransactions.toTransactionEntity(): TransactionEntity {
    val category = CategoryEntity(
        id = categoryId,
        name = categoryName,
        type = transactionType,
        icon = categoryIcon,
        color = categoryColor,
        isDefault = defaultCategory
    )
    val transactionEntity = TransactionEntity(
        id = transactionId,
        title = title,
        amount = amount,
        type = transactionType,
        category = category,
        dateCreated = dateCreated,
        note = note
    )

    return transactionEntity
}

fun GetTransactionBy.toTransactionEntity(): TransactionEntity {
    val category = CategoryEntity(
        id = categoryId,
        name = categoryName,
        type = transactionType,
        icon = categoryIcon,
        color = categoryColor,
        isDefault = defaultCategory
    )
    val transactionEntity = TransactionEntity(
        id = transactionId,
        title = title,
        amount = amount,
        type = transactionType,
        category = category,
        dateCreated = dateCreated,
        note = note
    )

    return transactionEntity
}

