package com.kakapo.oakane.common

suspend fun <T> proceed(executed: suspend () -> T): Result<T> {
    return try {
        Result.success(executed.invoke())
    } catch (e: Exception) {
        Result.failure(e)
    }
}