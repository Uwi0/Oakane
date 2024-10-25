package com.kakapo.oakane.data.database

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.Database

expect class MySqlDriverFactory {
    fun createDriver(): SqlDriver
}

fun addDefaultCategories(driver: SqlDriver) {
    val database = Database(driver)
    val categoryTable = database.categoryEntityQueries
    if (categoryTable.checkIfExist().executeAsOne() == 0L) {
        categoryTable.insertDefaultCategories()
    }
}

const val DATABASE_NAME = "oakane.db"