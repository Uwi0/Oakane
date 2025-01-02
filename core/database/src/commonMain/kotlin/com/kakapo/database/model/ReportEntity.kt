package com.kakapo.database.model

import com.kakapo.GenerateReportAllWallet

data class ReportEntity(
    val month: String,
    val categoryName: String,
    val categoryBudgetLimit: Double,
    val amountSpentInCategory: Double,
    val remainingCategoryBudget: Double,
    val walletName: String,
    val walletBalance: Double,
    val transactionTitle: String,
    val transactionAmount: Double,
    val transactionType: String,
    val transactionDate: String,
    val note: String
)

fun GenerateReportAllWallet.toReportEntity(): ReportEntity {
    return ReportEntity(
        month = Month,
        categoryName = Category_Name ?: "",
        categoryBudgetLimit = Category_Budget_Limit ?: 0.0,
        amountSpentInCategory = Amount_Spent_in_Category ?: 0.0,
        remainingCategoryBudget = Remaining_Category_Budget ?: 0.0,
        walletName = Wallet_Name ?: "",
        walletBalance = Wallet_Balance ?: 0.0,
        transactionTitle = Transaction_Title,
        transactionAmount = Transaction_Amount,
        transactionType = Transaction_Type,
        transactionDate = Transaction_Date,
        note = Note ?: ""
    )
}
