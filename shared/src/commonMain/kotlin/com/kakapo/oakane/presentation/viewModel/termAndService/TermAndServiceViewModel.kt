package com.kakapo.oakane.presentation.viewModel.termAndService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.SystemRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("TermAndServiceViewModelKt")
class TermAndServiceViewModel(
    private val systemRepository: SystemRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(TermAndServiceState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<TermAndServiceEffect>()

    fun handleEvent(event: TermAndServiceEvent) {
        when(event) {
            is TermAndServiceEvent.OnTerm1Checked -> _uiState.update { it.copy(isTerm1Checked = event.isChecked) }
            is TermAndServiceEvent.OnTerm2Checked -> _uiState.update { it.copy(isTerm2Checked = event.isChecked) }
            is TermAndServiceEvent.OnTerm3Checked -> _uiState.update { it.copy(isTerm3Checked = event.isChecked) }
            is TermAndServiceEvent.OnTerm4Checked -> _uiState.update { it.copy(isTerm4Checked = event.isChecked) }
            TermAndServiceEvent.OnAgreementButtonClicked -> saveTermAndServiceAgreement()
        }
    }

    private fun saveTermAndServiceAgreement() = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(TermAndServiceEffect.NavigateToOnBoarding)
        }
        systemRepository.saveTermAndServiceCondition().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(TermAndServiceEffect.ShowError(throwable?.message ?: "An error occurred"))
    }

    private fun emit(effect: TermAndServiceEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}