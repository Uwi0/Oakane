package com.kakapo.oakane.presentation.viewModel.wallets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletsViewModel(): ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(WalletsState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<WalletsEffect>()

    fun handleEvent(event: WalletsEvent){
        when(event){
            is WalletsEvent.NavigateBack -> emit(WalletsEffect.NavigateBack)
            is WalletsEvent.OnChange -> _uiState.update { it.copy(searchQuery = event.query) }
        }
    }

    private fun emit(effect: WalletsEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}