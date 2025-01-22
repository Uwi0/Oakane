package com.kakapo.data.model

import com.kakapo.database.model.TransactionCategoryEntity
import com.kakapo.database.model.TransactionEntity
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.report.ReportModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.model.transaction.asTransactionType

data class TransactionParam(
    val id: Long = 0,
    val walletId: Long = 0,
    val title: String,
    val amount: Double,
    val type: Long,
    val category: CategoryModel,
    val dateCreated: Long,
    val note: String?,
    val imageFile: String?
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
        note = note,
        imageFile = imageFile
    )
}

fun TransactionEntity.toModel(currency: Currency) = TransactionModel(
    id = id,
    walletId = walletId,
    title = title,
    type = type.asTransactionType(),
    dateCreated = dateCreated,
    category = category.toCategoryModel(),
    amount = amount,
    note = note ?: "",
    currency = currency
)

fun TransactionCategoryEntity.toReportModel(currency: Currency) = ReportModel(
    id = id,
    name = name,
    color = color,
    amount = totalTransaction,
    isExpense = type == TransactionType.Expense.ordinal.toLong(),
    isDefault = isDefault,
    icon = icon,
    currency = currency
)
