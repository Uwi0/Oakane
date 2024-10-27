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

    val categories get() = _selectedCategories.asStateFlow()
    private val _selectedCategories = MutableStateFlow<List<CategoryModel>>(emptyList())
    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())

    val selectedTab get() = _selectedTab.asStateFlow()
    private val _selectedTab = MutableStateFlow(TransactionType.Expense.ordinal)

    fun initializeData() {
        loadCategories()
    }

    fun onSelectedTab(index: Int) {
        _selectedTab.update { index }
        _selectedCategories.update { _categories.value.filter { it.type.ordinal == index } }
    }

    private fun loadCategories() = viewModelScope.launch {
        repository.loadCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _selectedCategories.update { categories.filter { it.type.ordinal == _selectedTab.value } }
                    _categories.update { categories }
                },
                onFailure = {
                    Logger.e(it) { "Failed to load categories ${it.message}" }
                }
            )
        }
    }
}