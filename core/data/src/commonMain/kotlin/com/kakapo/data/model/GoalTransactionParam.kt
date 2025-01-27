package com.kakapo.data.model

import com.kakapo.database.model.GoalTransactionEntity

data class GoalTransactionParam(
    val goalId: Long,
    val dateCreated: Long,
    val amount: Double,
    val walletId: Long,
    val note: String
){

    fun toGoalTransactionEntity(): GoalTransactionEntity {
        return GoalTransactionEntity(
            goalId = goalId,
            dateCreated = dateCreated,
            amount = amount,
            walletId = walletId,
            note = note
        )
    }
}
