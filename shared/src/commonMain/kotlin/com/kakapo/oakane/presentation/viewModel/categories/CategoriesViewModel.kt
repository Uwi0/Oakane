package com.kakapo.oakane.presentation.viewModel.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakapo.common.asCustomResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.CategoryRepository
import com.kakapo.data.repository.base.SystemRepository
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.system.Theme
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.native.ObjCName

@ObjCName("CategoriesViewModelKt")
class CategoriesViewModel(
    private val categoryRepository: CategoryRepository,
    private val systemRepository: SystemRepository
) : ViewModel() {

    @NativeCoroutinesState
    val uiState get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CategoriesState())

    @NativeCoroutines
    val uiEffect get() = _uiEffect.asSharedFlow()
    private val _uiEffect = MutableSharedFlow<CategoriesEffect>()

    fun initializeData(showDrawer: Boolean) {
        _uiState.update { it.copy(showDrawer = showDrawer) }
        loadCategories()
        loadTheme()
    }

    fun handleEvent(event: CategoriesEvent) {
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
            is CategoriesEvent.OnTapped -> _uiState.update { it.tapped(event.category) }
            is CategoriesEvent.SwipeToDeleteBy -> deleteCategoryBy(event.id)
            CategoriesEvent.SaveCategory -> saveClicked()
            CategoriesEvent.ConfirmIcon -> _uiState.update { it.confirmSelectedIcon() }
            CategoriesEvent.NavigateBack -> emit(effect = CategoriesEffect.NavigateBack)
            CategoriesEvent.OpenDrawer -> emit(effect = CategoriesEffect.OpenDrawer)
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
        _uiState.update { it.updateSheet(visibility) }
        if (!visibility) emit(CategoriesEffect.HideSheet)
    }

    private fun loadCategories() = viewModelScope.launch {
        val onSuccess: (List<CategoryModel>) -> Unit = { categories ->
            _uiState.update { it.updateCategories(categories) }
        }
        categoryRepository.loadCategories().asCustomResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun loadTheme() = viewModelScope.launch {
        val onSuccess: (Theme) -> Unit = { theme ->
            _uiState.update { it.copy(theme = theme) }
        }
        systemRepository.loadSavedTheme().fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun saveClicked() {
        val isEditMode = uiState.value.isEditMode
        if (isEditMode) updateCategory()
        else saveCategory()
    }

    private fun saveCategory() = viewModelScope.launch {
        val category = uiState.value.asCategoryModel()
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.updateSheet(visibility = false) }
            loadCategories()
        }
        categoryRepository.save(category).fold(
            onSuccess = onSuccess,
            onFailure = ::handleError
        )
    }

    private fun updateCategory() = viewModelScope.launch {
        val category = uiState.value.asCategoryModel()
        categoryRepository.update(category).fold(
            onSuccess = {
                _uiState.update { it.updateSheet(visibility = false) }
                loadCategories()
            },
            onFailure = ::handleError
        )
    }

    private fun deleteCategoryBy(id: Long) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            _uiState.update { it.copy(showSheet = false) }
            loadCategories()
        }
        categoryRepository.deleteCategoryBy(id).fold(
            onSuccess = onSuccess,
            onFailure =::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val message = throwable?.message ?: "Unknown error"
        emit(CategoriesEffect.ShowError(message))
    }

    private fun emit(effect: CategoriesEffect) = viewModelScope.launch {
        _uiEffect.emit(effect)
    }
}