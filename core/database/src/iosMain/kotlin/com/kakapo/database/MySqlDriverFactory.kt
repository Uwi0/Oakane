package com.kakapo.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kakapo.Database

actual class MySqlDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = NativeSqliteDriver(Database.Schema, DATABASE_NAME)
        addDefaultCategories(driver)
        addDefaultWallet(driver)
        return driver
    }
}