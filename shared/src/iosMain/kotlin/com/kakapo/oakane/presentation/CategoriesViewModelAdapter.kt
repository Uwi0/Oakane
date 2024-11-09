package com.kakapo.oakane.presentation

import co.touchlab.kermit.Logger
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesState
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CategoriesViewModelAdapter (
    private val viewModel: CategoriesViewModel,
    private val scope: CoroutineScope
){

    init {
        viewModel.initializeData()
    }

    fun handleEvent(event: CategoriesEvent) {
        viewModel.handleEvent(event)
    }

    fun observeData(onStateChange: (CategoriesState) -> Unit) {
        scope.launch {
            viewModel.uiState.collect { categories ->
                onStateChange.invoke(categories)
            }
        }
    }
}