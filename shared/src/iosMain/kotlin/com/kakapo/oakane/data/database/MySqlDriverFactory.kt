package com.kakapo.oakane.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kakapo.Database
import com.kakapo.oakane.data.database.DATABASE_NAME

actual class MySqlDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, DATABASE_NAME)
    }
}