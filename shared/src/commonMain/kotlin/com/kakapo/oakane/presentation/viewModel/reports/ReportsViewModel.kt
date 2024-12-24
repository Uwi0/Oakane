package com.kakapo.oakane.presentation.viewModel.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.oakane.domain.usecase.base.GetMonthlyBudgetOverviewUseCase
import com.kakapo.oakane.model.monthlyBudget.MonthlyBudgetOverViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("ReportsViewModelKt")
class ReportsViewModel(
    private val getMonthlyBudgetOverview: GetMonthlyBudgetOverviewUseCase
): ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(ReportsState())

    fun initializeData(){
        loadMonthlyBudgetOverView()
    }

    private fun loadMonthlyBudgetOverView() = viewModelScope.launch {
        val onSuccess: (MonthlyBudgetOverViewModel) -> Unit = { monthlyOverView ->
            _uiState.update { it.copy(monthlyOverView = monthlyOverView) }
        }
        getMonthlyBudgetOverview.execute().fold(
            onSuccess = onSuccess,
            onFailure = {}
        )
    }
}