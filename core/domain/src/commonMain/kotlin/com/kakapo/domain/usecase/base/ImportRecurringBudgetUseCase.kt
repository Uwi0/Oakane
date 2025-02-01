package com.kakapo.domain.usecase.base

interface ImportRecurringBudgetUseCase {
    suspend fun execute(): Result<Unit>
}