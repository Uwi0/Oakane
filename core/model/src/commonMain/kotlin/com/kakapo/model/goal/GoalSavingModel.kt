package com.kakapo.model.goal

import com.kakapo.common.formatDateWith
import com.kakapo.model.transaction.TransactionType

data class GoalSavingModel(
    val id: Long = 0L,
    val amount: Double = 2000.0,
    val note: String = "Just Note",
    val createdAt: Long = 1740616605029
){

    val formattedDateCreated: String get() {
        return createdAt.formatDateWith(DATE_FORMAT)
    }

    val uniqueId: String get() {
        return "goal_saving$id"
    }

    val transactionType: TransactionType get() = TransactionType.Expense

    companion object{
        private const val DATE_FORMAT = "dd MMM yyyy"
        fun default() = GoalSavingModel()
    }
}
