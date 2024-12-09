package com.kakapo.oakane.domain.usecase.base

import com.kakapo.oakane.data.model.CategoryLimitParam

interface ValidateCategoryLimitUseCase {
    suspend fun execute(categoryLimit: CategoryLimitParam, isEditMode: Boolean): Result<Unit>
}