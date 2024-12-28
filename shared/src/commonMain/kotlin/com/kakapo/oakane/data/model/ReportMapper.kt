package com.kakapo.oakane.data.model

import com.kakapo.oakane.common.toFormatIDRWithCurrency
import com.kakapo.oakane.data.database.model.ReportEntity
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