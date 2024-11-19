package com.kakapo.oakane.data.database.model

data class GoalEntity(
    val id: Long,
    val imageFile: String,
    val name: String,
    val targetAmount: Double,
    val note: String,
    val startDate: Long,
    val endDate: Long
)
