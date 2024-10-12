package com.kakapo.oakane.database

import app.cash.sqldelight.db.SqlDriver

expect class MySqlDriverFactory {
    fun createDriver(): SqlDriver
}

const val DATABASE_NAME = "oakane.db"