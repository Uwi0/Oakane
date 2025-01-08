package com.kakapo.data.model

import com.kakapo.common.toFormatCurrency
import com.kakapo.database.model.ReportEntity
import com.kakapo.model.report.ReportCsvModel

fun ReportEntity.toReportCsvModel(): ReportCsvModel {
    return ReportCsvModel(
        month = month,
        categoryName = categoryName,
        categoryBudgetLimit = categoryBudgetLimit.toFormatCurrency(),
        amountSpentInCategory = amountSpentInCategory.toFormatCurrency(),
        remainingCategoryBudget = remainingCategoryBudget.toFormatCurrency(),
        walletName = walletName,
        walletBalance = walletBalance.toFormatCurrency(),
        transactionTitle = transactionTitle,
        transactionAmount = transactionAmount.toFormatCurrency(),
        transactionType = transactionType,
        transactionDate = transactionDate,
        note = note
    )
}