package com.kakapo.oakane.model.report

import kotlin.native.ObjCName

@ObjCName("ReportCsvModelKt")
data class ReportCsvModel(
    val month: String,
    val categoryName: String,
    val categoryBudgetLimit: String,
    val amountSpentInCategory: String,
    val remainingCategoryBudget: String,
    val walletName: String,
    val walletBalance: String,
    val transactionTitle: String,
    val transactionAmount: String,
    val transactionType: String,
    val transactionDate: String,
    val note: String
)