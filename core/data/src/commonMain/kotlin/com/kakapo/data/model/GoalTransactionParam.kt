package com.kakapo.data.model

import com.kakapo.database.model.GoalSavingsEntity

data class GoalTransactionParam(
    val goalId: Long,
    val dateCreated: Long,
    val amount: Double,
    val walletId: Long,
    val note: String
){

    fun toGoalTransactionEntity(): GoalSavingsEntity {
        return GoalSavingsEntity(
            goalId = goalId,
            dateCreated = dateCreated,
            amount = amount,
            walletId = walletId,
            note = note
        )
    }
}
