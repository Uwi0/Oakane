package com.kakapo.database.model

data class GoalTransactionEntity(
    val id: Long = 0,
    val goalId: Long,
    val walletId: Long,
    val amount: Double,
    val note: String,
    val dateCreated: Long,
)
