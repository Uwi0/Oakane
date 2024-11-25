package com.kakapo.oakane.data.model

import com.kakapo.oakane.data.database.model.GoalEntity
import com.kakapo.oakane.model.GoalModel

fun GoalModel.toGoalEntity(): GoalEntity {
    return GoalEntity(
        id = 0,
        imageFile = fileName,
        name = goalName,
        targetAmount = amount,
        note = note,
        startDate = startDate,
        savedAmount = savedMoney,
        endDate = endDate
    )
}

fun GoalEntity.toGoalModel() = GoalModel(
    id = id,
    amount = targetAmount,
    goalName = name,
    startDate = startDate,
    endDate = endDate,
    fileName = imageFile,
    savedMoney = savedAmount,
    note = note
)