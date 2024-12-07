package com.kakapo.oakane.data.database.datasource.impl

import com.kakapo.oakane.data.database.datasource.base.CategoryLimitLocalDatasource
import com.kakapo.oakane.data.database.datasource.base.CategoryLimitRepository
import com.kakapo.oakane.data.database.model.CategoryLimitParam

class CategoryLimitRepositoryImpl(
    private val localDatasource: CategoryLimitLocalDatasource
) : CategoryLimitRepository {

    override suspend fun save(categoryLimit: CategoryLimitParam): Result<Unit> {
        return localDatasource.insert(categoryLimit.toEntity())
    }
}