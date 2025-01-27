package com.kakapo.model.goal

data class GoalSavingModel(
    val id: Long,
    val amount: Double,
    val note: String,
    val dateCreated: String
)
