package com.kakapo.domain.usecase.base

import com.kakapo.data.model.CategoryLimitParam

interface ValidateCategoryLimitUseCase {
    suspend fun execute(categoryLimit: CategoryLimitParam, isEditMode: Boolean): Result<Unit>
}