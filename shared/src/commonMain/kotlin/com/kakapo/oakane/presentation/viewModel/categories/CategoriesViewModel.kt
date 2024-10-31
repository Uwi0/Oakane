package com.kakapo.oakane.presentation.viewModel.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CategoriesUiState())

    val categories get() = _filteredCategories.asStateFlow()
    private val _filteredCategories = MutableStateFlow<List<CategoryModel>>(emptyList())

    fun initializeData() {
        loadCategories()
    }

    fun handleEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.Search -> onSearchQueryChanged(event.query)
            is CategoriesEvent.ChangeTab -> onSelectedTab(event.index)
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
}