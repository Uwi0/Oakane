package com.kakapo.oakane.data.database.model

import com.kakapo.GoalTable
import kotlinx.serialization.Serializable

@Serializable
data class GoalEntity(
    val id: Long,
    val imageFile: String,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val note: String,
    val startDate: Long,
    val endDate: Long
)

fun GoalTable.toGoalEntity() = GoalEntity(
    id = id,
    imageFile = imageFile,
    name = name,
    targetAmount = targetAmount,
    savedAmount = savedAmount,
    note = note,
    startDate = startDate,
    endDate = endDate,
)