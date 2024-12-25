package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.TransactionCategoryEntity
import com.kakapo.oakane.data.database.model.TransactionEntity
import com.kakapo.oakane.model.ReportModel
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
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
    val saveBalance: Double get() {
        return if(type == 0L) amount else -amount
    }

    fun toEntity() = TransactionEntity(
        id = id,
        walletId = walletId,
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
    walletId = walletId,
    title = title,
    type = type.asTransactionType(),
    dateCreated = dateCreated,
    category = category.toCategoryModel(),
    amount = amount,
    note = note ?: ""
)

fun TransactionCategoryEntity.toReportModel() = ReportModel(
    id = id,
    name = name,
    color = color,
    amount = totalTransaction,
    isExpense = type == TransactionType.Expense.ordinal.toLong(),
    isDefault = isDefault,
    icon = icon
)
