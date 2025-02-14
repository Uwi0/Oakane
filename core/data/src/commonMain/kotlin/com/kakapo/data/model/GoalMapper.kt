package com.kakapo.data.model

import com.kakapo.database.model.GoalEntity
import com.kakapo.model.Currency
import com.kakapo.model.goal.GoalModel

fun GoalModel.toGoalEntity(): GoalEntity {
    return GoalEntity(
        id = 0,
        imageFile = fileName,
        name = name,
        targetAmount = amount,
        note = note,
        startDate = startDate,
        savedAmount = savedMoney,
        endDate = endDate
    )
}

fun GoalEntity.toGoalModel(currency: Currency) = GoalModel(
    id = id,
    amount = targetAmount,
    name = name,
    startDate = startDate,
    endDate = endDate,
    fileName = imageFile,
    savedMoney = savedAmount,
    note = note,
    currency = currency
)