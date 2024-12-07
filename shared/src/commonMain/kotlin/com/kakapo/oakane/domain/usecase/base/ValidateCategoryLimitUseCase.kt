package com.kakapo.oakane.domain.usecase.base

interface ValidateCategoryLimitUseCase {
    suspend fun execute(monthlyBudgetId: Long, newLimitAmount: Double): Result<Boolean>
}