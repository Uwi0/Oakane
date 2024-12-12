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

fun addDefaultWallet(driver: SqlDriver){
    val database = Database(driver)
    val walletTable = database.walletEntityQueries
    if (walletTable.checkIfExist().executeAsOne() == 0L){
        walletTable.createDefaultWallet()
    }
}

const val DATABASE_NAME = "oakane.db"