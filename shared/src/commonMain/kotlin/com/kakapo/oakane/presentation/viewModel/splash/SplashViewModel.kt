package com.kakapo.oakane.presentation.viewModel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.data.repository.base.SystemRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("SplashViewModelKt")
class SplashViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    @NativeCoroutinesState
    val isAlreadyReadOnBoarding get() = _isAlreadyReadOnBoarding.asStateFlow()
    private val _isAlreadyReadOnBoarding = MutableStateFlow(false)

    @NativeCoroutinesState
    val isAlreadyReadTermAndService get() = _isAlreadyReadTermAndService.asStateFlow()
    private val _isAlreadyReadTermAndService = MutableStateFlow(false)

    fun initViewModel() {
        isAlreadyReadTermAndService()
        isAlreadyReadOnBoarding()
    }

    private fun isAlreadyReadOnBoarding() = viewModelScope.launch {
        settingsRepository.loadOnBoardingAlreadyRead().fold(
            onSuccess = { alreadyRead -> _isAlreadyReadOnBoarding.update { alreadyRead } },
            onFailure = {}
        )
    }

    private fun isAlreadyReadTermAndService() = viewModelScope.launch {
        settingsRepository.loadTermAndServiceCondition().fold(
            onSuccess = { alreadyRead -> _isAlreadyReadTermAndService.update { alreadyRead } },
            onFailure = {}
        )
    }
}