package com.kakapo.oakane.presentation.viewModel.reminder

import androidx.lifecycle.ViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReminderViewModel : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReminderState())

    fun handleEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.Dialog -> _uiState.update { it.copy(showDialog = event.shown) }
            is ReminderEvent.TimeSelected -> _uiState.update { it.updateSelectedTime(event) }
        }
    }
}