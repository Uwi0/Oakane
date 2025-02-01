package com.kakapo.data.model

import com.kakapo.database.model.MonthlyBudgetEntity
import com.kakapo.model.Currency
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class MonthlyBudgetParam(
    val id: Long = 0,
    val totalBudget: Double = 0.0,
    val spentAmount: Double = 0.0,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val createdAt: Long = 0,
    val updatedAt: Long = 0
){

    fun toEntity() = MonthlyBudgetEntity(
        id = id,
        totalBudget = totalBudget,
        spentAmount = spentAmount,
        startDate = startDate,
        endDate = endDate,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

@Serializable
data class MonthlyBudgetRecurring(
    val amount: Double = 0.0,
) {
    fun toEncodeString(): String {
        return Json.encodeToString(this)
    }

    companion object {
        fun fromEncodeString(encodedString: String): MonthlyBudgetRecurring {
            return Json.decodeFromString(encodedString)
        }
    }
}

fun MonthlyBudgetEntity.toMonthlyBudgetModel(currency: Currency) = MonthlyBudgetModel(
    id = id,
    totalBudget = totalBudget,
    currency = currency
)