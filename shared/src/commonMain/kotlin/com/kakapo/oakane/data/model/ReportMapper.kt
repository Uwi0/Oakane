package com.kakapo.oakane.data.model

import com.kakapo.common.toFormatIDRWithCurrency
import com.kakapo.database.model.ReportEntity
import com.kakapo.oakane.model.report.ReportCsvModel

fun ReportEntity.toReportCsvModel(): ReportCsvModel {
    return ReportCsvModel(
        month = month,
        categoryName = categoryName,
        categoryBudgetLimit = categoryBudgetLimit.toFormatIDRWithCurrency(),
        amountSpentInCategory = amountSpentInCategory.toFormatIDRWithCurrency(),
        remainingCategoryBudget = remainingCategoryBudget.toFormatIDRWithCurrency(),
        walletName = walletName,
        walletBalance = walletBalance.toFormatIDRWithCurrency(),
        transactionTitle = transactionTitle,
        transactionAmount = transactionAmount.toFormatIDRWithCurrency(),
        transactionType = transactionType,
        transactionDate = transactionDate,
        note = note
    )
}