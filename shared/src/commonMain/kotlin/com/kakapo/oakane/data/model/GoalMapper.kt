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
        endDate = endDate
    )
}