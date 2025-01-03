package com.kakapo.oakane.domain.usecase.base

import com.kakapo.data.model.CategoryLimitParam
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface ValidateCategoryLimitUseCase {
    @NativeCoroutines
    suspend fun execute(categoryLimit: CategoryLimitParam, isEditMode: Boolean): Result<Unit>
}