package com.kakapo.oakane.presentation.viewModel.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.SettingsRepository
import com.kakapo.model.reminder.Reminder
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("ReminderViewModelKt")
class ReminderViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReminderState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<ReminderEffect>()

    fun initData() {
        loadReminder()
    }

    fun handleEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.Dialog -> _uiState.update { it.copy(showDialog = event.shown) }
            is ReminderEvent.TimeSelected -> _uiState.update { it.updateSelectedTime(event) }
            is ReminderEvent.DaySelected -> _uiState.update { it.updateSelected(event.day) }
            is ReminderEvent.ToggleReminder -> _uiState.update { it.copy(enabledReminder = event.enabled) }
            ReminderEvent.NavigateUp -> emit(ReminderEffect.NavigateBack)
            ReminderEvent.SaveReminder -> saveReminder()
        }
    }

    private fun loadReminder() = viewModelScope.launch {
        val onSuccess: (Reminder) -> Unit = { reminder ->
            _uiState.update { it.copy(reminder = reminder) }
        }
        settingsRepository.loadReminder().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveReminder() = viewModelScope.launch {
        val reminder = uiState.value.asReminder()
        val onSuccess: (Unit) -> Unit = { emit(ReminderEffect.CreatedReminder(reminder)) }
        settingsRepository.saveReminder(reminder).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        emit(ReminderEffect.ShowError(throwable?.message ?: "Something went wrong"))
    }

    private fun emit(effect: ReminderEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}