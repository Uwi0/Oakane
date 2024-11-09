package com.kakapo.oakane.presentation.viewModel.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.oakane.data.repository.base.CategoryRepository
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.model.CategoriesSheetContent
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
    private val _uiState = MutableStateFlow(CategoriesState())

    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<CategoriesEffect>()

    fun initializeData() {
        loadCategories()
    }

    fun handleEvent(event: CategoriesEvent) {
        Logger.d{ "Categories_Event: $event" }
        when (event) {
            is CategoriesEvent.Search -> onSearchQueryChanged(event.query)
            is CategoriesEvent.ChangeTab -> onSelectedTab(event.index)
            is CategoriesEvent.ShowSheet -> handleSheet(event.visibility)
            is CategoriesEvent.ChangeCategory -> _uiState.update { it.copy(categoryName = event.name) }
            is CategoriesEvent.Selected -> _uiState.update { it.copy(selectedType = event.type) }
            is CategoriesEvent.ChangeSheet -> _uiState.update { it.copy(sheetContent = event.content) }
            is CategoriesEvent.SelectedColor -> _uiState.update { it.updateColor(event.hex) }
            is CategoriesEvent.SelectedIcon -> _uiState.update { it.updateIcon(event.name) }
            is CategoriesEvent.PickImage -> _uiState.update { it.updateFileName(event.file) }
            CategoriesEvent.SaveCategory -> saveCategory()
            CategoriesEvent.ConfirmIcon -> _uiState.update { it.confirmSelectedIcon() }
            is CategoriesEvent.OnTapped -> _uiState.update { it.tapped(event.category) }
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
        _uiState.update {
            it.updateSheet(visibility)
        }
        if (!visibility) emitEffect(CategoriesEffect.HideSheet)
    }

    private fun loadCategories() = viewModelScope.launch {
        repository.loadCategories().collect { result ->
            result.fold(
                onSuccess = { categories ->
                    _uiState.update { it.updateCategories(categories) }
                },
                onFailure = {
                    Logger.e(it) { "Failed to load categories ${it.message}" }
                }
            )
        }
    }

    private fun saveCategory() = viewModelScope.launch {
        val category = uiState.value.asCategoryModel()
        repository.save(category).fold(
            onSuccess = {
                _uiState.update { it.updateSheet(visibility = false)}
                loadCategories()
            },
            onFailure = {
                Logger.e(it) { "Failed to create category ${it.message}" }
            }
        )
    }

    private fun emitEffect(effect: CategoriesEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}