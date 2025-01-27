package com.kakapo.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kakapo.Database

actual class MySqlDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        val driver = AndroidSqliteDriver(Database.Schema, context, DATABASE_NAME)
        Database.Schema.migrate(driver, 1, 2)
        addDefaultCategories(driver)
        return driver
    }
}