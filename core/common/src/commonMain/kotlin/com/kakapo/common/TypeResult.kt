package com.kakapo.common

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface CustomResult<out T> {
    data class Success<T>(val data: T) : CustomResult<T>
    data class Error(val exception: Throwable? = null) : CustomResult<Nothing>
    data object Loading : CustomResult<Nothing>
}

@NativeCoroutines
fun <T> Flow<T>?.asResult(): Flow<CustomResult<T>> {
    return this?.map<T, CustomResult<T>> {
        com.kakapo.common.CustomResult.Success(it)
    }
        ?.catch {
            emit(com.kakapo.common.CustomResult.Error(it))
        }
        ?.onStart { emit(com.kakapo.common.CustomResult.Loading) }
        ?: flowOf(com.kakapo.common.CustomResult.Error(NullPointerException("Flow<Result<T>> is null")))
}

@NativeCoroutines
fun <T> Flow<Result<T>>?.asCustomResult(): Flow<CustomResult<T>> {
    return this?.map {
        if (it.isSuccess) {
            com.kakapo.common.CustomResult.Success(it.getOrThrow())
        } else {
            com.kakapo.common.CustomResult.Error(it.exceptionOrNull())
        }
    }
        ?.catch {
            emit(com.kakapo.common.CustomResult.Error(it))
        }
        ?.onStart { emit(com.kakapo.common.CustomResult.Loading) }
        ?: flowOf(com.kakapo.common.CustomResult.Error(NullPointerException("Flow<Result<T>> is null")))
}


@NativeCoroutines
suspend fun <T> Flow<CustomResult<T>>.subscribe(
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: (Throwable?) -> Unit = {}
) {
    this.collect { result ->
        when (result) {
            com.kakapo.common.CustomResult.Loading -> onLoading.invoke()
            is CustomResult.Error -> onError.invoke(result.exception)
            is CustomResult.Success -> onSuccess.invoke(result.data)
        }
    }
}

@NativeCoroutines
suspend fun <T> Flow<CustomResult<T>>.suspendSubscribe(
    onLoading: () -> Unit = {},
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (Throwable?) -> Unit = {}
) {
    this.collect { result ->
        when (result) {
            com.kakapo.common.CustomResult.Loading -> onLoading.invoke()
            is CustomResult.Error -> onError.invoke(result.exception)
            is CustomResult.Success -> onSuccess.invoke(result.data)
        }
    }
}