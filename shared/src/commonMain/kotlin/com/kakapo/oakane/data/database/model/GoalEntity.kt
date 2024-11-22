package com.kakapo.oakane.data.database.model

import com.kakapo.GoalTable

data class GoalEntity(
    val id: Long,
    val imageFile: String,
    val name: String,
    val targetAmount: Double,
    val note: String,
    val startDate: Long,
    val endDate: Long
)

fun GoalTable.toGoalEntity() = GoalEntity(
    id = id,
    imageFile = imageFile,
    name = name,
    targetAmount = targetAmount,
    note = note,
    startDate = startDate,
    endDate = endDate
)