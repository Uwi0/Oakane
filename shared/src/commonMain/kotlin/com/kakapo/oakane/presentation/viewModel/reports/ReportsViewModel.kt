package com.kakapo.oakane.presentation.viewModel.reports

import androidx.lifecycle.ViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.native.ObjCName

@ObjCName("ReportsViewModelKt")
class ReportsViewModel: ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReportsState())
}