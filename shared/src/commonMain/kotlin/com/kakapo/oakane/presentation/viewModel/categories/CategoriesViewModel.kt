package com.kakapo.oakane.presentation.viewModel.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: CategoryRepository
) : ViewModel() {

    val categories get() = _filteredCategories.asStateFlow()
    private val _filteredCategories = MutableStateFlow<List<CategoryModel>>(emptyList())
    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())

    val selectedTab get() = _selectedTab.asStateFlow()
    private val _selectedTab = MutableStateFlow(TransactionType.Expense.ordinal)

    fun initializeData() {
        loadCategories()
    }

    fun onSelectedTab(index: Int) {
        _selectedTab.update { index }
    }

    fun onSearchQueryChanged(query: String) {
        val filteredCategories = _categories.value.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _filteredCategories.update { filteredCategories }
    }

    private fun loadCategories() = viewModelScope.launch {
        repository.loadCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _categories.update { categories }
                    _filteredCategories.update { categories }
                },
                onFailure = {
                    Logger.e(it) { "Failed to load categories ${it.message}" }
                }
            )
        }
    }
}