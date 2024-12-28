package com.kakapo.oakane.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportCsvModel(
    @SerialName("Month")
    val month: String,
    @SerialName("Category_Name")
    val categoryName: String,
    @SerialName("Category_Budget_Limit")
    val categoryBudgetLimit: String,
    @SerialName("Amount_Spent_in_Category")
    val amountSpentInCategory: String,
    @SerialName("Remaining_Category_Budget")
    val remainingCategoryBudget: String,
    @SerialName("Wallet_Name")
    val walletName: String,
    @SerialName("Wallet_Balance")
    val walletBalance: String,
    @SerialName("Transaction_Title")
    val transactionTitle: String,
    @SerialName("Transaction_Amount")
    val transactionAmount: String,
    @SerialName("Transaction_Type")
    val transactionType: String,
    @SerialName("Transaction_Date")
    val transactionDate: String,
    @SerialName("Note")
    val note: String
)
