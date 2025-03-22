package com.kakapo.data.repository.base

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines

interface SettingsRepository {
    @NativeCoroutines
    suspend fun saveOnBoardingAlreadyRead(): Result<Unit>
    @NativeCoroutines
    suspend fun loadOnBoardingAlreadyRead(): Result<Boolean>
    @NativeCoroutines
    suspend fun changeBalanceVisibility(visibility: Boolean): Result<Boolean>
    @NativeCoroutines
    suspend fun isBalanceVisible(): Result<Boolean>
    @NativeCoroutines
    suspend fun saveTermAndServiceCondition(): Result<Unit>
    @NativeCoroutines
    suspend fun loadTermAndServiceCondition(): Result<Boolean>
}