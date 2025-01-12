package com.kakapo.data.model

import com.kakapo.database.model.ReportEntity
import com.kakapo.model.Currency
import com.kakapo.model.report.ReportCsvModel
import com.kakapo.model.toFormatCurrency

fun ReportEntity.toReportCsvModel(currency: Currency): ReportCsvModel {
    return ReportCsvModel(
        month = month,
        categoryName = categoryName,
        categoryBudgetLimit = categoryBudgetLimit.toFormatCurrency(currency),
        amountSpentInCategory = amountSpentInCategory.toFormatCurrency(currency),
        remainingCategoryBudget = remainingCategoryBudget.toFormatCurrency(currency),
        walletName = walletName,
        walletBalance = walletBalance.toFormatCurrency(currency),
        transactionTitle = transactionTitle,
        transactionAmount = transactionAmount.toFormatCurrency(currency),
        transactionType = transactionType,
        transactionDate = transactionDate,
        note = note
    )
}