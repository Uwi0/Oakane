package com.kakapo.database.model

import com.kakapo.GoalSavingTable
import kotlinx.serialization.Serializable

@Serializable
data class GoalSavingsEntity(
    val id: Long = 0,
    val goalId: Long,
    val walletId: Long,
    val amount: Double,
    val note: String,
    val dateCreated: Long,
)

fun GoalSavingTable.toGoalSavingEntity() = GoalSavingsEntity(
    id = id,
    goalId = goalId,
    walletId = walletId,
    amount = amount,
    note = note ?: "",
    dateCreated = dateCreated
)
