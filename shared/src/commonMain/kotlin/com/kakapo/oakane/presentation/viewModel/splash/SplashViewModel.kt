package com.kakapo.oakane.presentation.viewModel.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.SystemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val systemRepository: SystemRepository
) : ViewModel() {

    val isAlreadyReadOnBoarding get() = _isAlreadyReadOnBoarding.asStateFlow()
    private val _isAlreadyReadOnBoarding = MutableStateFlow(false)

    val isAlreadyReadTermAndService get() = _isAlreadyReadTermAndService.asStateFlow()
    private val _isAlreadyReadTermAndService = MutableStateFlow(false)

    fun initViewModel() {
        isAlreadyReadOnBoarding()
        isAlreadyReadTermAndService()
    }

    private fun isAlreadyReadOnBoarding() = viewModelScope.launch{
        systemRepository.loadOnBoardingAlreadyRead().fold(
            onSuccess = { alreadyRead -> _isAlreadyReadOnBoarding.update { alreadyRead } },
            onFailure = {}
        )
    }

    private fun isAlreadyReadTermAndService() = viewModelScope.launch {
        systemRepository.loadTermAndServiceCondition().fold(
            onSuccess = { alreadyRead -> _isAlreadyReadTermAndService.update { alreadyRead } },
            onFailure = {}
        )
    }
}