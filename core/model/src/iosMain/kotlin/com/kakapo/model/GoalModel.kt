package com.kakapo.model

import com.kakapo.model.goal.GoalModel

fun List<GoalModel>.swiftTake(n: Int): List<GoalModel> {
    return this.take(n)
}