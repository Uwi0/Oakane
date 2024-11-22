package com.kakapo.oakane.common

suspend fun <T> proceed(executed: suspend () -> T): Result<T> {
    return try {
        Result.success(executed.invoke())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend fun <T, R> proceedResult(
    executed: suspend () -> Result<T>,
    transform: (T) -> R
): Result<R> {
    return executed.invoke().fold(
        onSuccess = {
            runCatching {
                transform(it)
            }
        },
        onFailure = {
            Result.failure(it)
        }
    )
}
