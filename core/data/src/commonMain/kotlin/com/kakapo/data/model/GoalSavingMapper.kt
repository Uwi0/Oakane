package com.kakapo.data.model

import com.kakapo.common.toDateWith
import com.kakapo.database.model.GoalSavingsEntity
import com.kakapo.model.goal.GoalSavingModel

data class GoalSavingParam(
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

fun GoalSavingsEntity.toGoalSavingModel() = GoalSavingModel(
    id = id,
    amount = amount,
    note = note,
    dateCreated = dateCreated.toDateWith("dd MMM yyyy")
)
