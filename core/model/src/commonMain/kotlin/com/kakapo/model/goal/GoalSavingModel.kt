package com.kakapo.model.goal

import com.kakapo.common.formatDateWith

data class GoalSavingModel(
    val id: Long,
    val amount: Double,
    val note: String,
    val createdAt: Long
){

    val formattedDateCreated: String get() {
        return createdAt.formatDateWith(DATE_FORMAT)
    }

    val uniqueId: String get() {
        return "goal_saving$id"
    }

    companion object{
        private const val DATE_FORMAT = "dd MMM yyyy"
    }
}
