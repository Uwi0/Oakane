package com.kakapo.domain.usecase.base

interface SetRecurringBudgetUseCase {
    suspend fun execute(budget: String, categoryLimit: String): Result<Unit>
}