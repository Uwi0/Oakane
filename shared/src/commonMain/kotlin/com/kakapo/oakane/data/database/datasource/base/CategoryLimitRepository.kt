package com.kakapo.oakane.data.database.datasource.base

import com.kakapo.oakane.data.database.model.CategoryLimitParam

interface CategoryLimitRepository {
    suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit>
}