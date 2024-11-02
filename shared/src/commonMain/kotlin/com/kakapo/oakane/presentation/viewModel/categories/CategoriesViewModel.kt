package com.kakapo.oakane.presentation.viewModel.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.CategoryRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CategoriesUiState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<CategoriesEffect>()

    fun initializeData() {
        loadCategories()
    }

    fun handleEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.Search -> onSearchQueryChanged(event.query)
            is CategoriesEvent.ChangeTab -> onSelectedTab(event.index)
            is CategoriesEvent.ShowSheet ->  handleSheet(event.visibility)
            is CategoriesEvent.ChangeCategory -> _uiState.update { it.copy(categoryName = event.name) }
            is CategoriesEvent.Selected -> _uiState.update { it.copy(selectedType = event.type) }
        }
    }

    private fun onSelectedTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    private fun onSearchQueryChanged(query: String) {
        val filteredCategories = _uiState.value.categories.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _uiState.update { it.copy(filteredCategories = filteredCategories) }
    }

    private fun handleSheet(visibility: Boolean) {
        _uiState.update { it.copy(showSheet = visibility, categoryName = "") }
        if (!visibility) emitEffect(CategoriesEffect.HideSheet)
    }

    private fun loadCategories() = viewModelScope.launch {
        repository.loadCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _uiState.update { it.copy(
                        categories = categories,
                        filteredCategories = categories
                    ) }
                },
                onFailure = {
                    Logger.e(it) { "Failed to load categories ${it.message}" }
                }
            )
        }
    }

    private fun emitEffect(effect: CategoriesEffect)  = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}