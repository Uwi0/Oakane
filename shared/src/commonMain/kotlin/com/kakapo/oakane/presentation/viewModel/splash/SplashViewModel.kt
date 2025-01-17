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

    val isAlreadyRead get() = _isAlreadyRead.asStateFlow()
    private val _isAlreadyRead = MutableStateFlow(false)

    fun initViewModel() {
        isAlreadyReadOnBoarding()
    }

    private fun isAlreadyReadOnBoarding() = viewModelScope.launch{
        systemRepository.loadOnBoardingAlreadyRead().fold(
            onSuccess = { alreadyRead -> _isAlreadyRead.update { alreadyRead } },
            onFailure = {}
        )
    }
}