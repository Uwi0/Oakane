package com.kakapo.database.model

import com.kakapo.GetTransactionBy
import com.kakapo.GetTransactionCategory
import com.kakapo.GetTransactionCategoryBy
import com.kakapo.GetTransactions
import com.kakapo.GetTransactionsByWalletId
import com.kakapo.TransactionTable
import kotlinx.serialization.Serializable

@Serializable
data class TransactionEntity(
    val id: Long,
    val walletId: Long = 0,
    val categoryId: Long = 0,
    val title: String,
    val amount: Double,
    val type: Long,
    val category: CategoryEntity = CategoryEntity(),
    val dateCreated: Long,
    val note: String?,
    val imageFile: String?
)

data class TransactionCategoryEntity(
    val id: Long,
    val name: String,
    val type: Long,
    val icon: String,
    val color: String,
    val isDefault: Boolean,
    val totalTransaction: Double
)

fun TransactionTable.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        walletId = walletId,
        categoryId = category,
        title = title,
        amount = amount,
        type = type,
        dateCreated = dateCreated,
        note = note,
        imageFile = imageFile
    )
}

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
        note = note,
        imageFile = imageFile
    )

    return transactionEntity
}

fun GetTransactionsByWalletId.toTransactionEntity(): TransactionEntity {
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
        note = note,
        imageFile = imageFile
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
        walletId = walletId,
        title = title,
        amount = amount,
        type = transactionType,
        category = category,
        dateCreated = dateCreated,
        note = note,
        imageFile = imageFile
    )

    return transactionEntity
}

fun GetTransactionCategory.toTransactionCategoryEntity(): TransactionCategoryEntity {
    return TransactionCategoryEntity(
        id = categoryId,
        name = categoryName,
        type = transactionType,
        icon = categoryIcon,
        color = categoryColor,
        isDefault = defaultCategory == 1L,
        totalTransaction = totalAmount ?: 0.0
    )
}

fun GetTransactionCategoryBy.toTransactionCategoryEntity(): TransactionCategoryEntity {
    return TransactionCategoryEntity(
        id = categoryId,
        name = categoryName,
        type = transactionType,
        icon = categoryIcon,
        color = categoryColor,
        isDefault = defaultCategory == 1L,
        totalTransaction = totalAmount ?: 0.0
    )
}

